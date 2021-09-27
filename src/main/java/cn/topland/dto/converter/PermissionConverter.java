package cn.topland.dto.converter;

import cn.topland.dto.PermissionDTO;
import cn.topland.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionConverter {

    public List<PermissionDTO> toPermissionDTOs(List<Permission> permissions) {

        return permissions.stream().map(this::toPermissionDTO).collect(Collectors.toList());
    }

    public PermissionDTO toPermissionDTO(Permission permission) {

        PermissionDTO dto = new PermissionDTO();
        dto.setCollection(permission.getCollection());
        dto.setAction(permission.getAction());
        dto.setPermissions(permission.getPermissions());
        dto.setValidation(permission.getValidation());
        dto.setPresets(permission.getPresets());
        dto.setFields(permission.getFields());
        return dto;
    }
}