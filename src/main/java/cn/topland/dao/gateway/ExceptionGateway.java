package cn.topland.dao.gateway;

import cn.topland.entity.Exception;
import cn.topland.entity.Order;
import cn.topland.entity.User;
import cn.topland.entity.directus.ExceptionDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExceptionGateway extends BaseGateway {

    @Value("${directus.items.exception}")
    private String EXCEPTION_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<ExceptionDO>> EXCEPTIONS = new TypeReference<>() {
    };

    public List<ExceptionDO> saveAll(List<Exception> exceptions, String accessToken) throws InternalException {

        Reply result = directus.post(EXCEPTION_URI, tokenParam(accessToken), composeExceptions(exceptions));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, EXCEPTIONS);
        }
        throw new InternalException("添加异常失败");
    }

    public ExceptionDO update(Exception exception, String accessToken) throws InternalException {

        Reply result = directus.patch(EXCEPTION_URI + "/" + exception.getId(), tokenParam(accessToken), composeException(exception));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, ExceptionDO.class);
        }
        throw new InternalException("更新异常失败");
    }

    private JsonNode composeExceptions(List<Exception> exceptions) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        exceptions.forEach(exception -> {

            array.add(composeException(exception));

        });
        return array;
    }

    private JsonNode composeException(Exception exception) {

        ObjectNode node = (ObjectNode) JsonUtils.toJsonNode(ExceptionDO.from(exception));
        node.put("resolved", exception.getResolved() != null && exception.getResolved() ? 1 : 0);
        node.put("critical", exception.getCritical() != null && exception.getCritical() ? 1 : 0);
        node.put("optimal", exception.getOptimal() != null && exception.getOptimal() ? 1 : 0);
        node.set("copies", composeCopies(exception.getCopies()));
        node.set("owners", composeOwners(exception.getOwners()));
        node.set("orders", composeOrders(exception.getOrders()));
        return node;
    }

    private JsonNode composeOrders(List<Order> orders) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        if (CollectionUtils.isNotEmpty(orders)) {

            orders.forEach(order -> {

                array.add(composeOrder(order));
            });
        }
        return array;
    }

    private JsonNode composeOwners(List<User> owners) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        if (CollectionUtils.isNotEmpty(owners)) {

            owners.forEach(owner -> {

                array.add(composeOwner(owner));
            });
        }
        return array;
    }

    private JsonNode composeCopies(List<User> copies) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        if (CollectionUtils.isNotEmpty(copies)) {

            copies.forEach(copy -> {

                array.add(composeCopy(copy));
            });
        }
        return array;
    }

    private JsonNode composeOwner(User user) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("user_id", user.getId());
        return node;
    }

    private JsonNode composeCopy(User user) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("user_id", user.getId());
        return node;
    }

    private JsonNode composeOrder(Order order) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("order_id", order.getId());
        return node;
    }
}