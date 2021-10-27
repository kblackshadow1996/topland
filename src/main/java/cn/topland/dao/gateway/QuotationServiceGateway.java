package cn.topland.dao.gateway;

import cn.topland.entity.QuotationService;
import cn.topland.entity.directus.QuotationServiceDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuotationServiceGateway extends BaseGateway {

    @Value("${directus.items.quotation_service}")
    private String QUOTATION_SERVICE_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<QuotationServiceDO>> SERVICES = new TypeReference<>() {
    };

    public List<QuotationServiceDO> saveAll(List<QuotationService> services, String accessToken) {

        List<QuotationServiceDO> quotationServices = createAll(getCreateQuotationServices(services), accessToken);
        quotationServices.addAll(updateAll(getUpdateQuotationServices(services), accessToken));
        return quotationServices;
    }

    private List<QuotationServiceDO> createAll(List<QuotationService> services, String accessToken) {

        Reply result = directus.post(QUOTATION_SERVICE_URI, tokenParam(accessToken), composeQuotationService(services));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, SERVICES);
    }

    private List<QuotationServiceDO> updateAll(List<QuotationService> services, String accessToken) {

        List<QuotationServiceDO> quotationServiceDOs = new ArrayList<>();
        for (QuotationService service : services) {

            Reply result = directus.patch(QUOTATION_SERVICE_URI + "/" + service.getId(), tokenParam(accessToken),
                    JsonUtils.toJsonNode(QuotationServiceDO.from(service)));
            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            quotationServiceDOs.add(JsonUtils.parse(data, QuotationServiceDO.class));
        }
        return quotationServiceDOs;
    }

    private List<QuotationService> getCreateQuotationServices(List<QuotationService> services) {

        return services.stream().filter(service -> service.getId() == null).collect(Collectors.toList());
    }

    private List<QuotationService> getUpdateQuotationServices(List<QuotationService> services) {

        return services.stream().filter(service -> service.getId() != null).collect(Collectors.toList());
    }

    private JsonNode composeQuotationService(List<QuotationService> services) {

        List<QuotationServiceDO> quotationServiceDOs = services.stream().map(QuotationServiceDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(quotationServiceDOs);
    }
}