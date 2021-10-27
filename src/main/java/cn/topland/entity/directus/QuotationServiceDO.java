package cn.topland.entity.directus;

import cn.topland.entity.QuotationService;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class QuotationServiceDO extends DirectusIdEntity {

    private Long service;

    private String unit;

    private BigDecimal price;

    private Integer amount;

    private String explanation;

    private Long quotation;

    public static QuotationServiceDO from(QuotationService service) {

        QuotationServiceDO serviceDO = new QuotationServiceDO();
        serviceDO.setService(service.getService() == null ? null : service.getService().getId());
        serviceDO.setUnit(service.getUnit());
        serviceDO.setPrice(service.getPrice());
        serviceDO.setAmount(service.getAmount());
        serviceDO.setExplanation(service.getExplanation());
        serviceDO.setCreateTime(service.getCreateTime());
        serviceDO.setLastUpdateTime(service.getLastUpdateTime());
        serviceDO.setQuotation(service.getQuotation());
        return serviceDO;
    }
}