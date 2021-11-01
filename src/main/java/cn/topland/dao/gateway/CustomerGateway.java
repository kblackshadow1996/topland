package cn.topland.dao.gateway;

import cn.topland.entity.Customer;
import cn.topland.entity.directus.CustomerDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerGateway extends BaseGateway {

    @Value("${directus.items.customer}")
    private String CUSTOMER_URI;

    @Autowired
    private DirectusGateway directus;

    public CustomerDO save(Customer customer, String accessToken) {

        Reply result = directus.post(CUSTOMER_URI, tokenParam(accessToken), JsonUtils.toJsonNode(CustomerDO.from(customer)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, CustomerDO.class);
    }

    public CustomerDO update(Customer customer, String accessToken) {

        Reply result = directus.patch(CUSTOMER_URI + "/" + customer.getId(), tokenParam(accessToken),
                JsonUtils.toJsonNode(CustomerDO.from(customer)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, CustomerDO.class);
    }

    public CustomerDO lostAndRetrieve(Customer customer, String accessToken) {

        Reply result = directus.patch(CUSTOMER_URI + "/" + customer.getId(), tokenParam(accessToken),
                JsonUtils.toJsonNode(CustomerDO.lostAndRetrieve(customer)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, CustomerDO.class);
    }
}