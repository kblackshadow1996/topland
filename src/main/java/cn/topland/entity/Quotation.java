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
    private String identity;

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
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    /**
     * 品牌
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand")
    private Brand brand;

    /**
     * 套餐
     */
    @ManyToOne
    @JoinColumn(name = "package")
    private Package servicePackage;

    /**
     * 包含服务
     */
    @OneToMany
    @JoinColumn(name = "quotation")
    private List<QuotationService> services;

    /**
     * 销售经理id
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * pdf
     */
    @OneToOne
    @JoinColumn(name = "pdf")
    private Attachment pdf;
}