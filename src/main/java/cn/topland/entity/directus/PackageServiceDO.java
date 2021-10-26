package cn.topland.entity.directus;

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
}