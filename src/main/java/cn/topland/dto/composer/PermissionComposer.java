package cn.topland.dto.composer;

import cn.topland.dto.PermissionDTO;
import cn.topland.dto.converter.PermissionConverter;
import cn.topland.entity.Authority;
import cn.topland.entity.Permission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static cn.topland.dto.PermissionDTO.Operation;

/**
 * 组装directus_permissions数据
 */
@Component
public class PermissionComposer {

    @Autowired
    private PermissionConverter converter;

    public List<PermissionDTO> compose(List<Authority> oldAuths, List<Authority> newAuths, String directusRole) {

        List<Permission> oldPermissions = getPermissions(oldAuths);
        List<Permission> newPermissions = getPermissions(newAuths);
        return distinctPermissions(oldPermissions, newPermissions, directusRole);
    }

    private List<PermissionDTO> distinctPermissions(List<Permission> oldPermissions, List<Permission> newPermissions, String directusRole) {

        List<PermissionDTO> permissions = new ArrayList<>();
        List<Permission> intersection = (List<Permission>) CollectionUtils.intersection(oldPermissions, newPermissions);
        // 新增权限
        permissions.addAll(createPermissions(CollectionUtils.removeAll(newPermissions, intersection)));
        // 删除权限
        permissions.addAll(deletePermissions(CollectionUtils.removeAll(oldPermissions, intersection)));
        // 角色
        permissions.forEach(p -> {

            p.setRole(directusRole);
        });
        return permissions;
    }

    private List<PermissionDTO> createPermissions(Collection<Permission> permissions) {

        return permissions.stream().map(p -> {

            PermissionDTO permission = converter.toPermissionDTO(p);
            permission.setOperation(Operation.CREATE);
            return permission;
        }).collect(Collectors.toList());
    }

    private List<PermissionDTO> deletePermissions(Collection<Permission> permissions) {

        return permissions.stream().map(p -> {

            PermissionDTO permission = converter.toPermissionDTO(p);
            permission.setOperation(Operation.DELETE);
            return permission;
        }).collect(Collectors.toList());
    }

    private List<Permission> getPermissions(List<Authority> auths) {

        List<Permission> permissions = new ArrayList<>();
        auths.forEach(auth -> {

            permissions.addAll(auth.getPermissions());
        });
        return permissions.stream().distinct().collect(Collectors.toList());
    }
}