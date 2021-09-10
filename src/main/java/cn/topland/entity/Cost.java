package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 费用
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cost")
public class Cost extends IdEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 启用状态
     */
    private Boolean active;

    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "type")
    private CostType type;
}