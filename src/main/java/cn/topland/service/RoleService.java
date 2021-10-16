package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.entity.*;
import cn.topland.service.composer.PermissionComposer;
import cn.topland.vo.RoleVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private DirectusRolesRepository directusRolesRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private DirectusPermissionsRepository directusPermissionsRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionComposer permissionComposer;

    public Role get(Long id) {

        return repository.getById(id);
    }

    @Transactional
    public Role add(RoleVO roleVO, User creator) {

        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        List<DirectusPermissions> permissions = createPermissions(authorities);
        DirectusRoles directusRole = createDirectusRoles(roleVO, permissions);
        return repository.saveAndFlush(createRole(roleVO, authorities, directusRole, creator));
    }

    @Transactional
    public Role update(Long id, RoleVO roleVO, User editor) {

        Role role = repository.getById(id);
        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        List<DirectusPermissions> permissions = updatePermissions(role.getAuthorities(), authorities);
        DirectusRoles directusRole = updateDirectusRoles(role.getRole(), roleVO, permissions);
        return repository.saveAndFlush(updateRole(role, roleVO, authorities, directusRole, editor));
    }

    private Role updateRole(Role role, RoleVO roleVO, List<Authority> authorities, DirectusRoles directusRole, User editor) {

        composeRole(roleVO, authorities, directusRole, role);
        role.setEditor(editor);
        role.setLastUpdateTime(LocalDateTime.now());
        return role;
    }

    private DirectusRoles updateDirectusRoles(DirectusRoles role, RoleVO roleVO, List<DirectusPermissions> permissions) {

        role.setName(roleVO.getName());
        role.setPermissions(permissions);
        return directusRolesRepository.saveAndFlush(role);
    }

    private List<DirectusPermissions> updatePermissions(List<Authority> oldAuths, List<Authority> newAuths) {

        List<DirectusPermissions> oldPermissions = permissionComposer.compose(oldAuths);
        List<DirectusPermissions> newPermissions = permissionComposer.compose(newAuths);
        List<DirectusPermissions> intersection = (List<DirectusPermissions>) CollectionUtils.intersection(oldPermissions, newPermissions);
        List<DirectusPermissions> retain = (List<DirectusPermissions>) CollectionUtils.retainAll(oldPermissions, intersection);
        // 需要删除的部分
        Collection<DirectusPermissions> remove = CollectionUtils.removeAll(oldPermissions, intersection);
        if (CollectionUtils.isNotEmpty(remove)) {

            directusPermissionsRepository.deleteAll(remove);
        }
        // 需要新增的部分
        Collection<DirectusPermissions> add = CollectionUtils.removeAll(newPermissions, intersection);
        if (CollectionUtils.isNotEmpty(add)) {

            retain.addAll(directusPermissionsRepository.saveAllAndFlush(add));
        }
        return retain;
    }

    private List<DirectusPermissions> createPermissions(List<Authority> authorities) {

        List<DirectusPermissions> directusPermissions = permissionComposer.compose(authorities);
        directusPermissions.addAll(permissionComposer.createPermissions(listDefaultPermissions()));
        return CollectionUtils.isNotEmpty(directusPermissions)
                ? directusPermissionsRepository.saveAllAndFlush(directusPermissions)
                : List.of();
    }

    private List<Permission> listDefaultPermissions() {

        return permissionRepository.findByAuthorityIsNull();
    }

    private Role createRole(RoleVO roleVO, List<Authority> authorities, DirectusRoles directusRole, User creator) {

        Role role = new Role();
        composeRole(roleVO, authorities, directusRole, role);
        role.setCreator(creator);
        role.setEditor(creator);
        return role;
    }

    private void composeRole(RoleVO roleVO, List<Authority> authorities, DirectusRoles directusRole, Role role) {

        role.setRole(directusRole);
        role.setAuthorities(authorities);
        role.setName(roleVO.getName());
        role.setRemark(roleVO.getRemark());
    }

    private DirectusRoles createDirectusRoles(RoleVO roleVO, List<DirectusPermissions> permissions) {

        DirectusRoles directusRole = new DirectusRoles();
        directusRole.setName(roleVO.getName());
        directusRole.setPermissions(permissions);
        return directusRolesRepository.saveAndFlush(directusRole);
    }
}