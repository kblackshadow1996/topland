package cn.topland.service.composer;

import cn.topland.entity.Authority;
import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.Permission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组装directus_permissions数据
 */
@Component
public class PermissionComposer {

    public List<DirectusPermissions> compose(List<Authority> authorities) {

        return createPermissions(getPermissions(authorities));
    }

    public List<DirectusPermissions> createPermissions(Collection<Permission> permissions) {

        return permissions.stream().map(this::createPermission).collect(Collectors.toList());
    }

    private DirectusPermissions createPermission(Permission p) {

        DirectusPermissions directusPermission = new DirectusPermissions();
        directusPermission.setCollection(p.getCollection());
        directusPermission.setAction(p.getAction());
        directusPermission.setPermissions(p.getPermissions());
        directusPermission.setPresets(p.getPresets());
        directusPermission.setValidation(p.getValidation());
        directusPermission.setFields(p.getFields());
        return directusPermission;
    }

    private List<Permission> getPermissions(List<Authority> auths) {

        if (CollectionUtils.isEmpty(auths)) {

            return List.of();
        }
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