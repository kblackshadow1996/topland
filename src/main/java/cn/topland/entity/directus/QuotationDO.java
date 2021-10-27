package cn.topland.entity.directus;

import cn.topland.entity.Quotation;
import cn.topland.entity.QuotationComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class QuotationDO extends DirectusRecordEntity {

    private String title;

    private String identity;

    private BigDecimal subtotal;

    private BigDecimal discount;

    @JsonProperty(value = "subtotal_comment")
    private String subtotalComment;

    @JsonProperty(value = "discount_comment")
    private String discountComment;

    @JsonProperty(value = "total_comment")
    private String totalComment;

    private String explanations;

    private Long customer;

    private Long brand;

    @JsonProperty(value = "package")
    private Long servicePackage;

    private List<Long> services;

    private Long seller;

    public static QuotationDO from(Quotation quotation) {

        QuotationDO quotationDO = new QuotationDO();
        quotationDO.setTitle(quotation.getTitle());
        quotationDO.setIdentity(quotation.getIdentity());
        quotationDO.setSubtotal(quotation.getSubtotal());
        quotationDO.setDiscount(quotation.getDiscount());
        QuotationComment comment = quotation.getComment();
        quotationDO.setSubtotalComment(comment.getSubtotalComment());
        quotationDO.setDiscountComment(comment.getDiscountComment());
        quotationDO.setTotalComment(comment.getTotalComment());
        quotationDO.setExplanations(quotation.getExplanations());
        quotationDO.setCustomer(quotation.getCustomer() == null ? null : quotation.getCustomer().getId());
        quotationDO.setBrand(quotation.getBrand() == null ? null : quotation.getBrand().getId());
        quotationDO.setServicePackage(quotation.getServicePackage() == null ? null : quotation.getServicePackage().getId());
        quotationDO.setSeller(quotation.getSeller() == null ? null : quotation.getSeller().getId());
        quotationDO.setCreator(quotation.getCreator().getId());
        quotationDO.setEditor(quotation.getEditor().getId());
        quotationDO.setCreateTime(quotation.getCreateTime());
        quotationDO.setLastUpdateTime(quotation.getLastUpdateTime());
        return quotationDO;
    }
}