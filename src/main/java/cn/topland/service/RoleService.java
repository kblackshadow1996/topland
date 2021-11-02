package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.PermissionsGateway;
import cn.topland.dao.gateway.RoleGateway;
import cn.topland.dao.gateway.RolesGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.PermissionDO;
import cn.topland.entity.directus.RoleDO;
import cn.topland.entity.directus.RolesDO;
import cn.topland.service.composer.PermissionComposer;
import cn.topland.util.exception.QueryException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.RoleVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@PropertySource(value = "classpath:data.properties", encoding = "utf-8")
public class RoleService {

    @Value("${admin.role.name}")
    private String ADMIN;

    @Value("${default.role.name}")
    private String DEFAULT_ROLE;

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

    @Autowired
    private RoleGateway roleGateway;

    @Autowired
    private RolesGateway rolesGateway;

    @Autowired
    private PermissionsGateway permissionsGateway;

    public Role get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("角色[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public RoleDO add(RoleVO roleVO, User creator) {

        validateNameUnique(roleVO.getName());
        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        DirectusRoles directusRole = createDirectusRoles(roleVO, creator.getAccessToken());
        createPermissions(authorities, directusRole.getId(), creator.getAccessToken());
        return roleGateway.add(createRole(roleVO, directusRole, authorities, creator), creator.getAccessToken());
    }

    public RoleDO update(Long id, RoleVO roleVO, User editor) {

        Role role = get(id);
        validateNameUnique(roleVO.getName(), id);
        validateAdminOrUser(role.getName());
        List<Authority> authorities = authorityRepository.findAllById(roleVO.getAuthorities());
        DirectusRoles directusRole = updateDirectusRoles(role.getRole(), roleVO, editor.getAccessToken());
        updatePermissions(role.getRole().getPermissions(), authorities, directusRole.getId(), editor.getAccessToken());
        return roleGateway.update(updateRole(role, roleVO, authorities, directusRole, editor), editor.getAccessToken());
    }

    private void validateAdminOrUser(String name) {

        if (ADMIN.equals(name) || DEFAULT_ROLE.equals(name)) {

            throw new QueryException("[" + ADMIN + "]或[" + DEFAULT_ROLE + "]不可修改");
        }
    }

    private void validateNameUnique(String name) {

        if (repository.existsByName(name)) {

            throw new UniqueException("角色名称[" + name + "]重复");
        }
    }

    private void validateNameUnique(String name, Long id) {

        if (repository.existsByNameAndIdNot(name, id)) {

            throw new UniqueException("角色名称[" + name + "]重复");
        }
    }

    private Role updateRole(Role role, RoleVO roleVO, List<Authority> authorities, DirectusRoles directusRole, User editor) {

        composeRole(roleVO, authorities, directusRole, role);
        role.setEditor(editor);
        role.setLastUpdateTime(LocalDateTime.now());
        return role;
    }

    private DirectusRoles updateDirectusRoles(DirectusRoles role, RoleVO roleVO, String accessToken) {

        role.setName(roleVO.getName());
        rolesGateway.update(role, accessToken);
        return role;
    }

    private List<PermissionDO> updatePermissions(List<DirectusPermissions> oldPermissions, List<Authority> newAuths, String role, String accessToken) {

        List<DirectusPermissions> newPermissions = createWithDefaultPermissions(newAuths, role);
        List<DirectusPermissions> intersection = (List<DirectusPermissions>) CollectionUtils.intersection(oldPermissions, newPermissions);
        List<DirectusPermissions> retain = (List<DirectusPermissions>) CollectionUtils.retainAll(oldPermissions, intersection);
        // 需要删除的部分
        List<DirectusPermissions> remove = (List<DirectusPermissions>) CollectionUtils.removeAll(oldPermissions, retain);
        if (CollectionUtils.isNotEmpty(remove)) {

            permissionsGateway.removeAll(remove, accessToken);
        }
        // 需要新增的部分
        List<DirectusPermissions> add = (List<DirectusPermissions>) CollectionUtils.removeAll(newPermissions, intersection);
        return CollectionUtils.isNotEmpty(add)
                ? permissionsGateway.saveAll(add, accessToken)
                : List.of();
    }

    private List<PermissionDO> createPermissions(List<Authority> authorities, String role, String accessToken) {

        List<DirectusPermissions> directusPermissions = createWithDefaultPermissions(authorities, role);
        return CollectionUtils.isNotEmpty(directusPermissions)
                ? permissionsGateway.saveAll(directusPermissions, accessToken)
                : null;
    }

    private List<DirectusPermissions> createWithDefaultPermissions(List<Authority> authorities, String role) {

        List<DirectusPermissions> directusPermissions = permissionComposer.compose(authorities);
        directusPermissions.addAll(permissionComposer.createPermissions(listDefaultPermissions()));
        directusPermissions.forEach(p -> p.setRole(role));
        return directusPermissions;
    }

    private List<Permission> listDefaultPermissions() {

        return permissionRepository.findByAuthorityIsNull();
    }

    private Role createRole(RoleVO roleVO, DirectusRoles directusRole, List<Authority> authorities, User creator) {

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

    private DirectusRoles createDirectusRoles(RoleVO roleVO, String accessToken) {

        DirectusRoles directusRole = new DirectusRoles();
        directusRole.setName(roleVO.getName());
        RolesDO rolesDO = rolesGateway.add(directusRole, accessToken);
        directusRole.setId(rolesDO.getId());
        return directusRole;
    }
}