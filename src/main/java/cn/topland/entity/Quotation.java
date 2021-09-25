package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 报价
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "quotation")
public class Quotation extends IdEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 单号
     */
    private String number;

    /**
     * 小计
     */
    private BigDecimal subtotal;

    /**
     * 优惠金额
     */
    private BigDecimal discount;

    /**
     * 报价备注
     */
    @Embedded
    private QuotationComment comment;

    /**
     * 报价说明
     */
    private String explanations;

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
     * 包含服务
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "quotations_services",
            joinColumns = {@JoinColumn(name = "quotation_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<QuotationService> services;

    /**
     * 销售经理id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * pdf
     */
    @OneToOne
    @JoinColumn(name = "pdf")
    private Attachment pdf;
}