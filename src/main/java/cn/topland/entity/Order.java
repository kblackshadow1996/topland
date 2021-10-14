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
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    /**
     * 品牌
     */
    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    /**
     * 联系人
     */
    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    /**
     * 策划
     */
    @ManyToOne
    @JoinColumn(name = "planner")
    private User planner;

    /**
     * 制片
     */
    @ManyToOne
    @JoinColumn(name = "producer")
    private User producer;

    /**
     * 销售经理
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 拍摄月份
     */
    private String shootingMonth;

    /**
     * 异常
     */
    @ManyToMany(mappedBy = "orders")
    private List<Exception> exceptions;
}