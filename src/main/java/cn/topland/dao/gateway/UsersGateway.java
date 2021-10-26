package cn.topland.dao.gateway;

import cn.topland.entity.DirectusUsers;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UsersGateway extends BaseGateway {

    @Value("${directus.users}")
    private String USERS_URI;

    @Autowired
    private DirectusGateway directus;

    public void auth(DirectusUsers directusUser, String accessToken) throws InternalException {

        Reply result = directus.patch(USERS_URI + "/" + directusUser.getId(), tokenParam(accessToken), composeAuth(directusUser));
        if (!result.isSuccessful()) {

            throw new InternalException("授权失败");
        }
    }

    public List<DirectusUsers> saveAll(List<DirectusUsers> directusUsers, String accessToken) throws InternalException {

        List<DirectusUsers> users = directusUsers.stream().filter(user -> user.getId() == null).collect(Collectors.toList());
        Map<String, DirectusUsers> usersMap = directusUsers.stream().collect(Collectors.toMap(DirectusUsers::getEmail, u -> u));
        if (CollectionUtils.isNotEmpty(users)) {

            Reply result = directus.post(USERS_URI, tokenParam(accessToken), composeUsers(users));
            if (result.isSuccessful()) {

                JsonUtils.read(result.getContent()).path("data").forEach(user -> {

                    usersMap.get(user.path("email").asText()).setId(user.path("id").asText());
                });
            } else {

                throw new InternalException("创建用户失败");
            }
        }
        return new ArrayList<>(usersMap.values());
    }

    private JsonNode composeUsers(List<DirectusUsers> directusUsers) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        directusUsers.forEach(user -> {

            array.add(composeUser(user));
        });
        return array;
    }

    public JsonNode composeUser(DirectusUsers user) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("email", user.getEmail());
        node.put("password", user.getPassword());
        return node;
    }

    private JsonNode composeAuth(DirectusUsers directusUser) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("role", directusUser.getRole().getId());
        return node;
    }
}