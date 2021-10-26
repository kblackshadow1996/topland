package cn.topland.entity.directus;

import cn.topland.entity.Brand;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrandDO extends DirectusRecordEntity {

    private String name;

    private Long seller;

    private Long producer;

    private String business;

    private List<Long> contacts;

    private Long customer;

    public static BrandDO from(Brand brand) {

        BrandDO brandDO = new BrandDO();
        brandDO.setName(brand.getName());
        brandDO.setBusiness(brand.getBusiness());
        brandDO.setSeller(brand.getSeller() == null ? null : brand.getSeller().getId());
        brandDO.setCustomer(brand.getCustomer() == null ? null : brand.getCustomer().getId());
        brandDO.setProducer(brand.getProducer() == null ? null : brand.getProducer().getId());
        brandDO.setCreator(brand.getCreator().getId());
        brandDO.setEditor(brand.getEditor().getId());
        brandDO.setCreateTime(brand.getCreateTime());
        brandDO.setLastUpdateTime(brand.getLastUpdateTime());
        return brandDO;
    }
}