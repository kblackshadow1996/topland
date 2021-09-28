package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 服务
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "service")
public class Service extends IdEntity {

    /**
     * 服务等级
     */
    private Integer level;

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 费用
     */
    @ManyToMany
    @JoinTable(name = "service_cost",
            joinColumns = {@JoinColumn(name = "service_id")}, inverseJoinColumns = {@JoinColumn(name = "cost_id")})
    private List<Cost> costs;

    /**
     * 单位
     */
    private String unit;

    /**
     * 交付方式
     */
    private String delivery;

    /**
     * 父服务类型
     */
    @ManyToOne
    @JoinColumn(name = "parent")
    private Service parent;

    /**
     * 启用状态
     */
    private Boolean active;

    /**
     * 备注
     */
    private String remark;

    /**
     * 性质
     */
    private Type type;

    public enum Type {

        THIRD_PARTY, // 第三方
        BUSINESS // 主营
    }
}