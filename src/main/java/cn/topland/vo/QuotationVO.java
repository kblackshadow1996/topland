package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class QuotationVO implements Serializable {

    /**
     * 客户ID
     */
    private Long customer;

    /**
     * 品牌ID
     */
    private Long brand;

    /**
     * 销售ID
     */
    private Long seller;

    /**
     * 套餐ID
     */
    @JsonProperty(value = "package")
    private Long servicePackage;

    /**
     * 标题
     */
    private String title;

    /**
     * 小计
     */
    private BigDecimal subtotal;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 小计说明
     */
    @JsonProperty(value = "subtotal_comment")
    private String subtotalComment;

    /**
     * 折扣说明
     */
    @JsonProperty(value = "discount_comment")
    private String discountComment;

    /**
     * 总计说明
     */
    @JsonProperty(value = "total_comment")
    private String totalComment;

    /**
     * 报价说明
     */
    private String explanations;

    private List<QuotationServiceVO> services;

    /**
     * 创建人ID
     */
    private Long creator;
}