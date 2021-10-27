package cn.topland.dao.gateway;

import cn.topland.entity.DirectusRoles;
import cn.topland.entity.directus.RolesDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RolesGateway extends BaseGateway {

    @Value("${directus.roles}")
    private String ROLES_URI;

    @Autowired
    private DirectusGateway directus;

    public RolesDO add(DirectusRoles roles, String accessToken) {

        Reply result = directus.post(ROLES_URI, tokenParam(accessToken), JsonUtils.toJsonNode(RolesDO.from(roles)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, RolesDO.class);
    }

    public RolesDO update(DirectusRoles roles, String accessToken) {

        Reply result = directus.patch(ROLES_URI + "/" + roles.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(RolesDO.from(roles)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, RolesDO.class);
    }
}