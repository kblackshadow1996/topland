package cn.topland.dao.gateway;

import cn.topland.entity.Quotation;
import cn.topland.entity.directus.QuotationDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuotationGateway extends BaseGateway {

    @Value("${directus.items.quotation}")
    private String QUOTATION_URI;

    @Autowired
    private DirectusGateway directus;

    public QuotationDO save(Quotation quotation, String accessToken) {

        Reply result = directus.post(QUOTATION_URI, tokenParam(accessToken), JsonUtils.toJsonNode(QuotationDO.from(quotation)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, QuotationDO.class);
    }

    public QuotationDO update(Quotation quotation, String accessToken) {

        Reply result = directus.patch(QUOTATION_URI + "/" + quotation.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(QuotationDO.from(quotation)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, QuotationDO.class);
    }
}