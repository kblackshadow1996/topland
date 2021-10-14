package cn.topland.dto.converter;

import cn.topland.dto.QuotationServiceDTO;
import cn.topland.entity.QuotationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuotationServiceConverter extends BaseConverter<QuotationService, QuotationServiceDTO> {

    @Override
    public List<QuotationServiceDTO> toDTOs(List<QuotationService> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public QuotationServiceDTO toDTO(QuotationService quotationService) {

        return quotationService != null
                ? composeDTO(quotationService)
                : null;
    }

    private QuotationServiceDTO composeDTO(QuotationService quotationService) {

        QuotationServiceDTO dto = new QuotationServiceDTO();
        dto.setId(quotationService.getId());
        dto.setService(getId(quotationService.getService()));
        dto.setUnit(quotationService.getUnit());
        dto.setAmount(quotationService.getAmount());
        dto.setPrice(quotationService.getPrice());
        dto.setExplanation(quotationService.getExplanation());
        return dto;
    }
}