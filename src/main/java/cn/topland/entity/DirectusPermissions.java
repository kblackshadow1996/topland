package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}