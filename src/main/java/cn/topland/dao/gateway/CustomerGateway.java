package cn.topland.dao.gateway;

import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import cn.topland.entity.directus.CustomerDO;
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
public class CustomerGateway extends BaseGateway {

    @Value("${directus.items.customer}")
    private String CUSTOMER_URI;

    @Autowired
    private DirectusGateway directus;

    public CustomerDO save(Customer customer, String accessToken) throws InternalException {

        Reply result = directus.post(CUSTOMER_URI, tokenParam(accessToken), composeCustomer(customer));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, CustomerDO.class);
        }
        throw new InternalException("保存客户失败");
    }

    public CustomerDO update(Customer customer, String accessToken) throws InternalException {

        Reply result = directus.patch(CUSTOMER_URI + "/" + customer.getId(), tokenParam(accessToken), composeCustomer(customer));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, CustomerDO.class);
        }
        throw new InternalException("更新客户失败");
    }

    private JsonNode composeCustomer(Customer customer) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", customer.getName());
        node.put("seller", customer.getSeller().getId());
        node.put("business", customer.getBusiness());
        node.put("type", customer.getType().name());
        node.put("parent", customer.getParent() == null ? null : customer.getParent().getId());
        node.put("source", customer.getSource().name());
        node.put("status", customer.getStatus().name());
        Invoice invoice = customer.getInvoice();
        node.put("invoice_type", invoice.getInvoiceType().name());
        node.put("identity", invoice.getIdentity());
        node.put("post_address", invoice.getPostAddress());
        node.put("register_address", invoice.getRegisterAddress());
        node.put("mobile", invoice.getMobile());
        node.put("bank", invoice.getBank());
        node.put("account", invoice.getAccount());
        node.put("creator", customer.getCreator().getId());
        node.put("editor", customer.getEditor().getId());
        node.put("create_time", FORMATTER.format(customer.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(customer.getLastUpdateTime()));
        return node;
    }
}