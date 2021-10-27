package cn.topland.dao.gateway;

import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import cn.topland.entity.directus.RoleDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
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
public class RoleGateway extends BaseGateway {

    @Value("${directus.items.role}")
    private String ROLE_URI;

    @Autowired
    private DirectusGateway directus;

    public RoleDO add(Role role, String accessToken) {

        Reply result = directus.post(ROLE_URI, tokenParam(accessToken), JsonUtils.toJsonNode(RoleDO.from(role)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, RoleDO.class);
    }

    public RoleDO update(Role role, String accessToken) {

        Reply result = directus.patch(ROLE_URI + "/" + role.getId(), tokenParam(accessToken), composeRole(role));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, RoleDO.class);
    }

    private JsonNode composeRole(Role role) {

        ObjectNode node = (ObjectNode) JsonUtils.toJsonNode(RoleDO.from(role));
        node.set("authorities", composeAuths(role.getAuthorities()));
        return node;
    }

    private JsonNode composeAuths(List<Authority> authorities) {

        ArrayNode auths = JsonNodeFactory.instance.arrayNode();
        if (CollectionUtils.isNotEmpty(authorities)) {

            authorities.forEach(auth -> {

                auths.add(composeAuth(auth));
            });
        }
        return auths;
    }

    private JsonNode composeAuth(Authority auth) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("authority_id", auth.getId());
        return node;
    }
}