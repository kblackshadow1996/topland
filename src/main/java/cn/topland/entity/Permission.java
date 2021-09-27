package cn.topland.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "permission")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Permission extends SimpleIdEntity {

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

    /**
     * 是否初始字段
     */
    private Boolean init;
}