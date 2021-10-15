package cn.topland.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "`order`")
public class Order extends RecordEntity {

    /**
     * 编号
     */
    @Column(name = "unique")
    private String identity;

    /**
     * 客户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    /**
     * 品牌
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand")
    private Brand brand;

    /**
     * 联系人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact")
    private Contact contact;

    /**
     * 策划
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner")
    private User planner;

    /**
     * 制片
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer")
    private User producer;

    /**
     * 销售经理
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 拍摄月份
     */
    private String shootingMonth;

    /**
     * 异常
     */
    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Exception> exceptions;
}