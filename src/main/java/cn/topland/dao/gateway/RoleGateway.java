package cn.topland.dao.gateway;

import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import cn.topland.entity.directus.RoleDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleGateway extends BaseGateway {

    @Value("${directus.items.role}")
    private String ROLE_URI;

    @Autowired
    private DirectusGateway directus;

    public RoleDO add(Role role, String accessToken) throws InternalException {

        Reply result = directus.post(ROLE_URI, tokenParam(accessToken), composeRole(role));
        System.out.println(result.getContent());
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, RoleDO.class);
        }
        throw new InternalException("新建角色失败");
    }

    public RoleDO update(Role role, String accessToken) throws InternalException {

        Reply result = directus.patch(ROLE_URI + "/" + role.getId(), tokenParam(accessToken), composeRole(role));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, RoleDO.class);
        }
        throw new InternalException("更新角色失败");
    }

    private JsonNode composeRole(Role role) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", role.getName());
        node.put("directus_role", role.getRole().getId());
        node.set("authorities", composeAuths(role.getAuthorities()));
        node.put("remark", role.getRemark());
        node.put("creator", role.getCreator().getId());
        node.put("editor", role.getEditor().getId());
        node.put("create_time", FORMATTER.format(role.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(role.getCreateTime()));
        return node;
    }

    private JsonNode composeAuths(List<Authority> authorities) {

        ArrayNode auths = JsonNodeFactory.instance.arrayNode();
        authorities.forEach(auth -> {

            auths.add(composeAuth(auth));
        });
        return auths;
    }

    private JsonNode composeAuth(Authority auth) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("authority_id", auth.getId());
        return node;
    }
}