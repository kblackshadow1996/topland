package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 报价服务
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "quotation_service")
public class QuotationService extends IdEntity {

    /**
     * 服务
     */
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    /**
     * 单位
     */
    private String unit;

    /**
     * 服务单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 说明
     */
    private String explanation;
}