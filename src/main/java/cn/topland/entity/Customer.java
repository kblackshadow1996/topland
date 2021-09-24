package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String seller;

    /**
     * 经营信息
     */
    private String business;

    /**
     * 公司性质
     */
    private Type type;

    /**
     * 来源
     */
    private Source source;

    /**
     * 状态
     */
    private Status status;

    /**
     * 发票信息
     */
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    /**
     * 联系人
     */
    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Contact> contacts = new ArrayList<>();

    /**
     * 品牌
     */
    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Brand> brands = new ArrayList<>();

    /**
     * 操作记录
     */
    @OneToMany
    @JoinColumn(name = "customer_id")
    @OrderBy("createTime asc")
    private List<Operation> operations = new ArrayList<>();

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