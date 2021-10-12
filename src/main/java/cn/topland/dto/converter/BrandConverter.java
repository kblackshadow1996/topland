package cn.topland.dto.converter;

import cn.topland.dto.BrandDTO;
import cn.topland.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter extends BaseConverter<Brand, BrandDTO> {

    @Autowired
    private ContactConverter contactConverter;

    @Override
    public BrandDTO toDTO(Brand brand) {

        return brand != null
                ? composeBrandDTO(brand)
                : null;
    }

    private BrandDTO composeBrandDTO(Brand brand) {

        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setBusiness(brand.getBusiness());
        dto.setSeller(getUserName(brand.getSeller()));
        dto.setProducer(getUserName(brand.getProducer()));
        dto.setContracts(contactConverter.toDTOs(brand.getContacts()));
        dto.setCreator(getUserName(brand.getCreator()));
        dto.setEditor(getUserName(brand.getEditor()));
        dto.setCreateTime(brand.getCreateTime());
        dto.setLastUpdateTime(brand.getLastUpdateTime());
        return dto;
    }
}