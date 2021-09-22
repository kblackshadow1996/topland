package cn.topland.service;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.SimpleIdEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private static final String SEPARATOR = "♣";

    public List<Long> distinct(List<DirectusPermissions> permissions) {

        return permissions.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(this::getDistinctString))), ArrayList::new))
                .stream().map(SimpleIdEntity::getId).collect(Collectors.toList());
    }

    // 根据所有属性去重
    private String getDistinctString(DirectusPermissions permission) {

        return permission.getCollection() + SEPARATOR
                + permission.getAction() + SEPARATOR
                + permission.getPermissions() + SEPARATOR
                + permission.getValidation() + SEPARATOR
                + permission.getPresets() + SEPARATOR
                + permission.getFields();
    }
}