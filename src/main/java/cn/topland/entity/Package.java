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
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "package")
    private List<PackageService> services;

    /**
     * 备注
     */
    private String remark;
}