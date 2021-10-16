package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * directus权限表
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "directus_permissions")
public class DirectusPermissions extends SimpleIdEntity {

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

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {

            return false;
        }
        DirectusPermissions that = (DirectusPermissions) o;
        return equalsTo(that);
    }

    private boolean equalsTo(DirectusPermissions that) {

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
}