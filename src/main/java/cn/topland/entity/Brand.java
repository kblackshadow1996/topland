package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 品牌信息
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand extends RecordEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 销售
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 制片
     */
    @ManyToOne
    @JoinColumn(name = "producer")
    private User producer;

    /**
     * 经营信息
     */
    private String business;

    /**
     * 联系人
     */
    @OneToMany(targetEntity = Contact.class)
    @JoinColumn(name = "brand")
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @OneToMany(mappedBy = "brand")
    private List<Order> orders;

    public enum Action {

        CREATE, // 新建
        UPDATE // 更新
    }
}