package cn.topland.dao.gateway;

import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.entity.directus.UserDO;
import cn.topland.util.DirectusGateway;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.AccessException;
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

    public UserDO login(User user) throws AccessException, InternalException {

        Reply result = directus.post(LOGIN_URI, null, user.loginInfo());
        if (result.isSuccessful()) {

            return cacheToken(user, JsonUtils.parse(result.getContent()));
        }
        throw new AccessException("账号异常");
    }

    public void logout(User user) throws InternalException {

        Reply result = directus.post(LOGOUT_URI, null, user.logoutInfo());
        if (!result.isSuccessful()) {

            throw new InternalException("logout failed");
        }
    }

    public void refreshToken(User user) throws InternalException {

        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("refresh_token", user.getRefreshToken());
        Reply result = directus.post(REFRESH_URI + "/" + user.getId(), tokenParam(user.getAccessToken()), body);
        if (!result.isSuccessful()) {

            throw new InternalException("刷新token失败");
        }
    }

    public List<UserDO> saveAll(List<User> users, String accessToken) throws InternalException {

        List<UserDO> userDOs = addUsers(getCreateUsers(users), accessToken);
        userDOs.addAll(updateUsers(getUpdateUsers(users), accessToken));
        return userDOs;
    }

    public List<UserDO> auth(List<User> users, String accessToken) throws InternalException {

        Reply result = directus.patch(USER_URI, tokenParam(accessToken), composeAuth(users));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, USERS);
        }
        throw new InternalException("授权失败");
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

    private List<UserDO> updateUsers(List<User> users, String accessToken) throws InternalException {

        List<UserDO> userDOs = new ArrayList<>();
        for (User user : users) {

            // 更新用户只能单个进行
            Reply result = directus.patch(USER_URI + "/" + user.getId(), tokenParam(accessToken), composeUser(user));
            if (result.isSuccessful()) {

                userDOs.add(JsonUtils.parse(JsonUtils.read(result.getContent()).path("data").toPrettyString(), UserDO.class));
            } else {

                throw new InternalException("同步更新用户[" + user.getUserId() + "]失败");
            }
        }
        return userDOs;
    }

    private List<UserDO> addUsers(List<User> users, String accessToken) throws InternalException {

        Reply result = directus.post(USER_URI, tokenParam(accessToken), composeUsers(users));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, USERS);
        }
        throw new InternalException("同步创建用户失败");
    }

    private List<User> getUpdateUsers(List<User> users) {

        return users.stream().filter(u -> u.getId() != null).collect(Collectors.toList());
    }

    private List<User> getCreateUsers(List<User> users) {

        return users.stream().filter(u -> u.getId() == null).collect(Collectors.toList());
    }

    private UserDO cacheToken(User user, Map<String, Object> tokens) throws InternalException {

        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("access_token", (String) tokens.get("access_token"));
        body.put("refresh_token", (String) tokens.get("refresh_token"));
        Reply result = directus.patch(USER_URI + "/" + user.getId(), tokenParam((String) tokens.get("access_token")), body);
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, UserDO.class);
        }
        throw new InternalException("缓存token失败");
    }

    private JsonNode composeUsers(List<User> users) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        users.forEach(user -> {

            array.add(composeUser(user));
        });
        return array;
    }

    private ObjectNode composeUser(User user) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("user_id", user.getUserId());
        node.put("name", user.getName());
        node.put("email", user.getEmail());
        node.put("external_position", user.getExternalPosition());
        node.put("internal_position", user.getInternalPosition());
        node.put("mobile", user.getMobile());
        node.put("avatar", user.getAvatar());
        node.set("departments", composeDepartments(user.getDepartments()));
        node.put("lead_departments", user.getLeadDepartments());
        node.put("source", user.getSource().name());
        node.put("active", user.getActive() ? 1 : 0);
        node.put("directus_user", user.getDirectusUser().getId());
        node.put("directus_email", user.getDirectusEmail());
        node.put("directus_password", user.getDirectusPassword());
        node.put("creator", user.getCreator().getId());
        node.put("editor", user.getEditor().getId());
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