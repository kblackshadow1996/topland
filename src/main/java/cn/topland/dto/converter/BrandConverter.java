package cn.topland.dto.converter;

import cn.topland.dto.BrandDTO;
import cn.topland.entity.directus.BrandDO;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter extends BaseConverter<BrandDO, BrandDTO> {

    @Override
    public BrandDTO toDTO(BrandDO brand) {

        return brand != null
                ? composeBrandDTO(brand)
                : null;
    }

    private BrandDTO composeBrandDTO(BrandDO brand) {

        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setBusiness(brand.getBusiness());
        dto.setCustomer(brand.getCustomer());
        dto.setSeller(brand.getSeller());
        dto.setProducer(brand.getProducer());
        dto.setContacts(brand.getContacts());
        dto.setCreator(brand.getCreator());
        dto.setEditor(brand.getEditor());
        dto.setCreateTime(brand.getCreateTime());
        dto.setLastUpdateTime(brand.getLastUpdateTime());
        return dto;
    }
}