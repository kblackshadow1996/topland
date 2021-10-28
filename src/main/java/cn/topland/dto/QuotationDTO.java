package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class QuotationDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long customer;

    /**
     * 品牌ID
     */
    private Long brand;

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
     * 编号
     */
    private String identity;

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

    /**
     * 服务ID
     */
    private List<Long> services;

    /**
     * 销售ID
     */
    private Long seller;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 修改人ID
     */
    private Long editor;

    /**
     * 创建时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}