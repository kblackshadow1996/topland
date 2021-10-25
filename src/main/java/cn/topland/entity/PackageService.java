package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 套餐服务
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "package_service")
public class PackageService extends IdEntity {

    /**
     * 服务
     */
    @ManyToOne
    @JoinColumn(name = "service")
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
     * 交付类型
     */
    private String delivery;

    @Transient
    private Long pkgId;
}