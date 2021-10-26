package cn.topland.entity.directus;

import cn.topland.entity.PackageService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PackageServiceDO extends DirectusIdEntity {

    private Long service;

    private String unit;

    private BigDecimal price;

    private String delivery;

    @JsonProperty(value = "package")
    private Long pkgId;

    public static PackageServiceDO from(PackageService packageService) {

        PackageServiceDO packageServiceDO = new PackageServiceDO();
        packageServiceDO.setService(packageService.getService() == null ? null : packageService.getService().getId());
        packageServiceDO.setUnit(packageService.getUnit());
        packageServiceDO.setPrice(packageService.getPrice());
        packageServiceDO.setDelivery(packageService.getDelivery());
        packageServiceDO.setPkgId(packageService.getPkgId());
        packageServiceDO.setCreateTime(packageService.getCreateTime());
        packageServiceDO.setLastUpdateTime(packageService.getLastUpdateTime());
        return packageServiceDO;
    }
}