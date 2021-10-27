package cn.topland.dao.gateway;

import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.entity.directus.UserDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserGateway extends BaseGateway {

    @Value("${directus.items.user}")
    private String USER_URI;

    @Value("${directus.auth.login}")
    private String LOGIN_URI;

    @Value("${directus.auth.refresh}")
    private String REFRESH_URI;

    @Value("${directus.auth.logout}")
    private String LOGOUT_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<UserDO>> USERS = new TypeReference<>() {
    };

    public UserDO login(User user) {

        Reply result = directus.post(LOGIN_URI, null, user.loginInfo());
        return cacheToken(user, JsonUtils.parse(result.getContent()));
    }

    public void logout(User user) {

        directus.post(LOGOUT_URI, null, user.logoutInfo());
    }

    public String refreshToken(User user) {

        JsonNode token = refresh(user);
        UserDO userDO = cacheToken(user, Map.of("access_token", token.path("access_token").asText(),
                "refresh_token", token.path("refresh_token").asText()));
        return userDO.getAccessToken();
    }

    public List<UserDO> saveAll(List<User> users, String accessToken) {

        List<UserDO> userDOs = addUsers(getCreateUsers(users), accessToken);
        userDOs.addAll(updateUsers(getUpdateUsers(users), accessToken));
        return userDOs;
    }

    public List<UserDO> auth(List<User> users, String accessToken) {

        Reply result = directus.patch(USER_URI, tokenParam(accessToken), composeAuth(users));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, USERS);
    }

    private JsonNode refresh(User user) {

        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("refresh_token", user.getRefreshToken());
        Reply reply = directus.post(REFRESH_URI + "/" + user.getId(), tokenParam(user.getAccessToken()), body);
        return JsonUtils.read(reply.getContent()).path("data");
    }

    private UserDO cacheToken(User user, Map<String, Object> tokens) throws InternalException {

        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("access_token", (String) tokens.get("access_token"));
        body.put("refresh_token", (String) tokens.get("refresh_token"));
        Reply result = directus.patch(USER_URI + "/" + user.getId(), tokenParam((String) tokens.get("access_token")), body);
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, UserDO.class);
    }

    private List<UserDO> updateUsers(List<User> users, String accessToken) {

        List<UserDO> userDOs = new ArrayList<>();
        for (User user : users) {

            // 更新用户只能单个进行
            Reply result = directus.patch(USER_URI + "/" + user.getId(), tokenParam(accessToken), composeUser(user));
            userDOs.add(JsonUtils.parse(JsonUtils.read(result.getContent()).path("data").toPrettyString(), UserDO.class));
        }
        return userDOs;
    }

    private List<UserDO> addUsers(List<User> users, String accessToken) {

        Reply result = directus.post(USER_URI, tokenParam(accessToken), composeUsers(users));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, USERS);
    }

    private JsonNode composeAuth(List<User> users) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.set("keys", listUserIds(users));
        node.set("data", composeAuthData(users.get(0)));
        return node;
    }

    private JsonNode composeAuthData(User user) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("role", user.getRole().getId());
        node.put("auth", user.getAuth().name());
        return node;
    }

    private JsonNode listUserIds(List<User> users) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        users.forEach(user -> {

            array.add(user.getId());
        });
        return array;
    }

    private List<User> getUpdateUsers(List<User> users) {

        return users.stream().filter(u -> u.getId() != null).collect(Collectors.toList());
    }

    private List<User> getCreateUsers(List<User> users) {

        return users.stream().filter(u -> u.getId() == null).collect(Collectors.toList());
    }

    private JsonNode composeUsers(List<User> users) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        users.forEach(user -> {

            array.add(composeUser(user));
        });
        return array;
    }

    private ObjectNode composeUser(User user) {

        ObjectNode node = (ObjectNode) JsonUtils.toJsonNode(UserDO.from(user));
        node.set("departments", composeDepartments(user.getDepartments()));
        return node;
    }

    private JsonNode composeDepartments(List<Department> departments) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        departments.forEach(department -> {

            ObjectNode dept = JsonNodeFactory.instance.objectNode();
            dept.put("department_id", department.getId());
            array.add(dept);
        });
        return array;
    }
}