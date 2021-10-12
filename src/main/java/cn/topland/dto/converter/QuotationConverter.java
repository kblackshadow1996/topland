package cn.topland.dto.converter;

import cn.topland.dto.QuotationDTO;
import cn.topland.entity.Package;
import cn.topland.entity.*;
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
        dto.setSeller(getUserId(quotation.getSeller()));
        dto.setSubtotal(quotation.getSubtotal());
        dto.setDiscount(quotation.getDiscount());
        dto.setCustomer(getCustomerId(quotation.getCustomer()));
        dto.setBrand(getBrandId(quotation.getBrand()));
        dto.setServicePackage(getPackageId(quotation.getServicePackage()));
        dto.setServices(quotationServiceConverter.toDTOs(quotation.getServices()));
        dto.setExplanations(quotation.getExplanations());

        QuotationComment comment = quotation.getComment();
        dto.setSubtotalComment(comment.getSubtotalComment());
        dto.setDiscountComment(comment.getDiscountComment());
        dto.setTotalComment(comment.getTotalComment());

        dto.setCreator(getUserId(quotation.getCreator()));
        dto.setCreateTime(quotation.getCreateTime());
        return dto;
    }

    private Long getPackageId(Package servicePackage) {

        return servicePackage != null
                ? servicePackage.getId()
                : null;
    }

    private Long getCustomerId(Customer customer) {

        return customer != null
                ? customer.getId()
                : null;
    }

    private Long getBrandId(Brand brand) {

        return brand != null
                ? brand.getId()
                : null;
    }
}