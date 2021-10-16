package cn.topland.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "permission")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Permission extends SimpleIdEntity implements Cloneable {

    /**
     * 数据集
     */
    private String collection;

    /**
     * 行为
     */
    private String action;

    /**
     * 权限
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String permissions;

    /**
     * 验证
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String validation;

    /**
     * 预设
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String presets;

    /**
     * 字段
     */
    private String fields;

    @ManyToOne
    @JoinColumn(name = "authority")
    private Authority authority;

    @Override
    public boolean equals(Object that) {

        if (this == that) {

            return true;
        }
        if (that == null || getClass() != that.getClass()) {

            return false;
        }
        return equalsTo((Permission) that);
    }

    private boolean equalsTo(Permission that) {

        return Objects.equals(this.collection, that.collection)
                && Objects.equals(this.action, that.action)
                && Objects.equals(this.permissions, that.permissions)
                && Objects.equals(this.validation, that.validation)
                && Objects.equals(this.presets, that.presets)
                && Objects.equals(this.fields, that.fields);
    }

    @Override
    public int hashCode() {

        return Objects.hash(collection, action, permissions, validation, presets, fields);
    }

    @Override
    public Permission clone() {

        Permission permission = new Permission();
        permission.setCollection(collection);
        permission.setAction(action);
        permission.setPermissions(permissions);
        permission.setPresets(presets);
        permission.setValidation(validation);
        permission.setFields(fields);
        return permission;
    }
}