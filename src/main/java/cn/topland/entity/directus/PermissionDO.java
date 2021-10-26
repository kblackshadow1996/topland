package cn.topland.entity.directus;

import cn.topland.entity.DirectusPermissions;
import cn.topland.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionDO extends DirectusSimpleIdEntity {

    private String role;

    private String collection;

    private String action;

    private JsonNode permissions;

    private JsonNode validation;

    private JsonNode presets;

    private String fields;

    public static PermissionDO from(DirectusPermissions permission) {

        PermissionDO permissionDO = new PermissionDO();
        permissionDO.setCollection(permission.getCollection());
        permissionDO.setAction(permission.getAction());
        permissionDO.setPermissions(JsonUtils.read(permission.getPermissions()));
        permissionDO.setValidation(JsonUtils.read(permission.getValidation()));
        permissionDO.setPresets(JsonUtils.read(permission.getPresets()));
        permissionDO.setFields(permission.getFields());
        return permissionDO;
    }
}