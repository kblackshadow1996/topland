package cn.topland.entity.directus;

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
}