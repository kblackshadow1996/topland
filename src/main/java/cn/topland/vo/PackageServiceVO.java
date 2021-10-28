package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class PackageServiceVO implements Serializable {

    /**
     * 套餐服务ID, 新建服务不用传
     */
    private Long id;

    /**
     * 服务ID
     */
    private Long service;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 交付方式
     */
    private String delivery;
}