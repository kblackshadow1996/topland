package cn.topland.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class QuotationServiceDTO implements Serializable {

    private Long id;

    private Long service;

    private String unit;

    private BigDecimal price;

    private Integer amount;

    private String explanation;
}