package cn.topland.dto.converter;

import cn.topland.dto.PermissionDTO;
import cn.topland.entity.Permission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionConverter extends BaseConverter<Permission, PermissionDTO> {

    @Override
    public List<PermissionDTO> toDTOs(List<Permission> permissions) {

        return CollectionUtils.isEmpty(permissions)
                ? List.of()
                : permissions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PermissionDTO toDTO(Permission permission) {

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