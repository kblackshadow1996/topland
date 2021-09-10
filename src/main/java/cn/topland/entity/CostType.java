package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 费用类型
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cost_type")
public class CostType extends IdEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 启用状态
     */
    private Boolean active;

    /**
     * 备注
     */
    private String remark;
}