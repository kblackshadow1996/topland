package cn.topland.dto.composer;

import cn.topland.dto.PermissionDTO;
import cn.topland.dto.converter.PermissionConverter;
import cn.topland.entity.Authority;
import cn.topland.entity.Permission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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
        List<Permission> distinct = permissions.stream().distinct().collect(Collectors.toList());
        // 合并fields
        return mergeFields(distinct);
    }

    private List<Permission> mergeFields(List<Permission> permissions) {

        Map<String, List<Permission>> permissionMap = new HashMap<>();
        List<Permission> merges = new ArrayList<>();
        permissions.stream().filter(p -> !"*".equals(p.getFields())).forEach(p -> {

            // 集合和action可以唯一索引出某条数据
            String unique = p.getCollection() + p.getAction();
            List<Permission> permission = CollectionUtils.isEmpty(permissionMap.get(unique))
                    ? new ArrayList<>()
                    : permissionMap.get(unique);
            permission.add(p);
            permissionMap.put(unique, permission);
            merges.add(p);
        });
        permissions.removeAll(merges);
        permissionMap.forEach((unique, children) -> {

            if (CollectionUtils.isNotEmpty(children)) {

                List<String> fields = new ArrayList<>();
                children.forEach(p -> {

                    fields.addAll(List.of(p.getFields().split(",")));
                });
                permissions.add(mergeFields(children.get(0), fields));
            }
        });
        return permissions;
    }

    private Permission mergeFields(Permission permission, List<String> fields) {

        Permission merge = permission.clone();
        merge.setFields(getSortedString(fields));
        return merge;
    }

    // 组装fields时，顺序对于判断删除某条数据十分重要，否则可能判断失败，所以统一排序后再返回
    private String getSortedString(List<String> fields) {

        return fields.stream().sorted().collect(Collectors.joining(","));
    }
}