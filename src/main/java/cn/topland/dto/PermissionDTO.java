package cn.topland.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
public class PermissionDTO implements Serializable {

    private String role;

    private String collection;

    private String action;

    private String permissions;

    private String validation;

    private String presets;

    private String fields;

    /**
     * 区分删除和新增
     */
    private Operation operation;

    public enum Operation {

        CREATE,
        DELETE
    }
}