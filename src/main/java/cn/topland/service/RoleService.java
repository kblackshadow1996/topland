package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.entity.*;
import cn.topland.service.composer.PermissionComposer;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.RoleVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@PropertySource(value = "classpath:data.properties", encoding = "utf-8")
public class RoleService {

    @Value("${admin.role.name}")
    private String ADMIN;

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

        validateNameUnique(roleVO.getName());
        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        List<DirectusPermissions> permissions = createPermissions(authorities);
        DirectusRoles directusRole = createDirectusRoles(roleVO, permissions);
        return repository.saveAndFlush(createRole(roleVO, authorities, directusRole, creator));
    }

    @Transactional
    public Role update(Long id, RoleVO roleVO, User editor) throws AccessException {

        validateNameUnique(roleVO.getName(), id);
        Role role = repository.getById(id);
        validateAdmin(role.getName());
        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        List<DirectusPermissions> permissions = updatePermissions(role.getRole().getPermissions(), authorities);
        DirectusRoles directusRole = updateDirectusRoles(role.getRole(), roleVO, permissions);
        return repository.saveAndFlush(updateRole(role, roleVO, authorities, directusRole, editor));
    }

    private void validateAdmin(String name) throws AccessException {

        if (ADMIN.equals(name)) {

            throw new AccessException("[管理员]角色不可修改");
        }
    }

    private void validateNameUnique(String name) {

        if (repository.existsByName(name)) {

            throw new UniqueException("角色名称" + "[" + name + "]" + "重复");
        }
    }

    private void validateNameUnique(String name, Long id) {

        if (repository.existsByNameAndIdNot(name, id)) {

            throw new UniqueException("角色名称" + "[" + name + "]" + "重复");
        }
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

    private List<DirectusPermissions> updatePermissions(List<DirectusPermissions> oldPermissions, List<Authority> newAuths) {

        List<DirectusPermissions> newPermissions = createWithDefaultPermissions(newAuths);
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

        List<DirectusPermissions> directusPermissions = createWithDefaultPermissions(authorities);
        return CollectionUtils.isNotEmpty(directusPermissions)
                ? directusPermissionsRepository.saveAllAndFlush(directusPermissions)
                : null;
    }

    private List<DirectusPermissions> createWithDefaultPermissions(List<Authority> authorities) {

        List<DirectusPermissions> directusPermissions = permissionComposer.compose(authorities);
        directusPermissions.addAll(permissionComposer.createPermissions(listDefaultPermissions()));
        return directusPermissions;
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