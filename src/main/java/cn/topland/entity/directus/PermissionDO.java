package cn.topland.entity.directus;

import cn.topland.entity.DirectusPermissions;
import cn.topland.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
        permissionDO.setPermissions(permission.getPermissions() != null
                ? JsonUtils.read(permission.getPermissions())
                : JsonNodeFactory.instance.objectNode());
        permissionDO.setValidation(permission.getValidation() != null
                ? JsonUtils.read(permission.getValidation())
                : JsonNodeFactory.instance.objectNode());
        permissionDO.setPresets(permission.getPresets() != null
                ? JsonUtils.read(permission.getPresets())
                : JsonNodeFactory.instance.objectNode());
        permissionDO.setFields(permission.getFields());
        return permissionDO;
    }
}