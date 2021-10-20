package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 型号
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "model")
public class Model extends IdEntity {

    /**
     * 型号
     */
    @Column(unique = true)
    private String model;

    /**
     * 类型
     */
    private String type;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 备注
     */
    private String remark;

    /**
     * 启用状态
     */
    private Boolean active;
}