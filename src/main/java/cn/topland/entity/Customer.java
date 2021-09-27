package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 客户
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends RecordEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 销售经理
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 经营信息
     */
    private String business;

    /**
     * 公司性质
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * 母集团
     */
    @ManyToOne
    @JoinColumn(name = "parent")
    private Customer parent;

    /**
     * 来源
     */
    @Enumerated(EnumType.STRING)
    private Source source;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 发票信息
     */
    @OneToOne
    @JoinColumn(name = "invoice")
    private Invoice invoice;

    /**
     * 联系人
     */
    @OneToMany
    @JoinColumn(name = "customer")
    private List<Contact> contacts;

    /**
     * 品牌
     */
    @OneToMany
    @JoinColumn(name = "customer")
    private List<Brand> brands;

    /**
     * 操作记录
     */
    @OneToMany
    @JoinColumn(name = "customer")
    private List<Operation> operations;

    public enum Status {

        COOPERATING, // 合作中
        LOST // 流失
    }

    public enum Type {

        GROUP, // 集团
        COMPANY // 公司
    }

    public enum Source {

        ACTIVE, // 自我(主动)
        RESOURCE // 资源
    }

    public enum Action {

        CREATE, // 新建
        UPDATE, // 更新
        LOST, // 流失
        RETRIEVE // 寻回
    }
}