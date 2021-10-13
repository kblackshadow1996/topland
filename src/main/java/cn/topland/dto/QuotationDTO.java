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

    private Long id;

    private Long customer;

    private Long brand;

    @JsonProperty(value = "package")
    private Long servicePackage;

    private String title;

    private String identity;

    private BigDecimal subtotal;

    private BigDecimal discount;

    @JsonProperty(value = "subtotal_comment")
    private String subtotalComment;

    @JsonProperty(value = "discount_comment")
    private String discountComment;

    @JsonProperty(value = "total_comment")
    private String totalComment;

    private String explanations;

    private List<QuotationServiceDTO> services;

    private Long seller;

    private Long creator;

    private Long editor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}