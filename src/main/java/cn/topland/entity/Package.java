package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 套餐
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "package")
public class Package extends RecordEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 包含服务
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "packages_services",
            joinColumns = {@JoinColumn(name = "package_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<PackageService> services;

    /**
     * 备注
     */
    private String remark;
}