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

    private Long customer;

    private Long brand;

    private Long seller;

    @JsonProperty(value = "package")
    private Long servicePackage;

    private String title;

    private BigDecimal subtotal;

    private BigDecimal discount;

    @JsonProperty(value = "subtotal_comment")
    private String subtotalComment;

    @JsonProperty(value = "discount_comment")
    private String discountComment;

    @JsonProperty(value = "total_comment")
    private String totalComment;

    private String explanations;

    private List<QuotationServiceVO> services;

    private Long creator;
}