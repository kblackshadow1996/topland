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
    @JoinColumn(name = "service_id")
    private Service service;

    /**
     * 服务单价
     */
    private BigDecimal price;
}