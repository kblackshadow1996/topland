package cn.topland.dto.converter;

import cn.topland.dto.QuotationDTO;
import cn.topland.entity.Quotation;
import cn.topland.entity.QuotationComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuotationConverter extends BaseConverter<Quotation, QuotationDTO> {

    @Autowired
    private QuotationServiceConverter quotationServiceConverter;

    @Override
    public QuotationDTO toDTO(Quotation quotation) {

        return quotation != null
                ? composerQuotationDTO(quotation)
                : null;
    }

    private QuotationDTO composerQuotationDTO(Quotation quotation) {

        QuotationDTO dto = new QuotationDTO();
        dto.setId(quotation.getId());
        dto.setTitle(quotation.getTitle());
        dto.setIdentity(quotation.getIdentity());
        dto.setSeller(getId(quotation.getSeller()));
        dto.setSubtotal(quotation.getSubtotal());
        dto.setDiscount(quotation.getDiscount());
        dto.setCustomer(getId(quotation.getCustomer()));
        dto.setBrand(getId(quotation.getBrand()));
        dto.setServicePackage(getId(quotation.getServicePackage()));
        dto.setServices(quotationServiceConverter.toDTOs(quotation.getServices()));
        dto.setExplanations(quotation.getExplanations());

        QuotationComment comment = quotation.getComment();
        dto.setSubtotalComment(comment.getSubtotalComment());
        dto.setDiscountComment(comment.getDiscountComment());
        dto.setTotalComment(comment.getTotalComment());

        dto.setCreator(getId(quotation.getCreator()));
        dto.setEditor(getId(quotation.getEditor()));
        dto.setCreateTime(quotation.getCreateTime());
        dto.setLastUpdateTime(quotation.getLastUpdateTime());
        return dto;
    }
}