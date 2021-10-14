package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class PackageServiceVO implements Serializable {

    private Long id;

    private Long service;

    private String unit;

    private BigDecimal price;

    private String delivery;
}