package cn.topland.dao.gateway;

import cn.topland.entity.DirectusRoles;
import cn.topland.entity.directus.RolesDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RolesGateway extends BaseGateway {

    @Value("${directus.roles}")
    private String ROLES_URI;

    @Autowired
    private DirectusGateway directus;

    public RolesDO add(DirectusRoles roles, String accessToken) throws InternalException {

        Reply result = directus.post(ROLES_URI, tokenParam(accessToken), JsonUtils.toJsonNode(RolesDO.from(roles)));
        if (result.isSuccessful()) {

            System.out.println(result.getContent());
            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, RolesDO.class);
        }
        throw new InternalException("新建角色失败");
    }

    public RolesDO update(DirectusRoles roles, String accessToken) throws InternalException {

        Reply result = directus.patch(ROLES_URI + "/" + roles.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(RolesDO.from(roles)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, RolesDO.class);
        }
        throw new InternalException("更新角色失败");
    }
}