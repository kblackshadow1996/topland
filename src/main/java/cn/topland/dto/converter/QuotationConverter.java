package cn.topland.dto.converter;

import cn.topland.dto.QuotationDTO;
import cn.topland.entity.directus.QuotationDO;
import org.springframework.stereotype.Component;

@Component
public class QuotationConverter extends BaseConverter<QuotationDO, QuotationDTO> {

    @Override
    public QuotationDTO toDTO(QuotationDO quotation) {

        return quotation != null
                ? composerQuotationDTO(quotation)
                : null;
    }

    private QuotationDTO composerQuotationDTO(QuotationDO quotation) {

        QuotationDTO dto = new QuotationDTO();
        dto.setId(quotation.getId());
        dto.setTitle(quotation.getTitle());
        dto.setIdentity(quotation.getIdentity());
        dto.setSeller(quotation.getSeller());
        dto.setSubtotal(quotation.getSubtotal());
        dto.setDiscount(quotation.getDiscount());
        dto.setCustomer(quotation.getCustomer());
        dto.setBrand(quotation.getBrand());
        dto.setServicePackage(quotation.getServicePackage());
        dto.setServices(quotation.getServices());
        dto.setExplanations(quotation.getExplanations());

        dto.setSubtotalComment(quotation.getSubtotalComment());
        dto.setDiscountComment(quotation.getDiscountComment());
        dto.setTotalComment(quotation.getTotalComment());

        dto.setCreator(quotation.getCreator());
        dto.setEditor(quotation.getEditor());
        dto.setCreateTime(quotation.getCreateTime());
        dto.setLastUpdateTime(quotation.getLastUpdateTime());
        return dto;
    }
}