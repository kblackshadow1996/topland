package cn.topland.dao.gateway;

import cn.topland.entity.Brand;
import cn.topland.entity.directus.BrandDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BrandGateway extends BaseGateway {

    @Value("directus.items.brand")
    private String BRAND_URL;

    @Autowired
    private DirectusGateway directus;

    public BrandDO save(Brand brand, String accessToken) throws InternalException {

        Reply result = directus.post(BRAND_URL, tokenParam(accessToken), composeBrand(brand));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, BrandDO.class);
        }
        throw new InternalException("创建品牌失败");
    }

    public BrandDO update(Brand brand, String accessToken) throws InternalException {

        Reply result = directus.patch(BRAND_URL + "/" + brand.getId(), tokenParam(accessToken), composeBrand(brand));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, BrandDO.class);
        }
        throw new InternalException("创建品牌失败");
    }

    private JsonNode composeBrand(Brand brand) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", brand.getName());
        node.put("seller", brand.getSeller() == null ? null : brand.getSeller().getId());
        node.put("producer", brand.getProducer() == null ? null : brand.getProducer().getId());
        node.put("business", brand.getBusiness());
        node.put("customer", brand.getCustomer().getId());
        node.put("creator", brand.getCreator().getId());
        node.put("editor", brand.getEditor().getId());
        node.put("create_time", FORMATTER.format(brand.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(brand.getLastUpdateTime()));
        return node;
    }
}