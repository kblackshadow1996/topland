package cn.topland.dao.gateway;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.directus.PermissionDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionsGateway extends BaseGateway {

    @Value("${directus.permissions}")
    private String PERMISSIONS_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<PermissionDO>> PERMISSIONS = new TypeReference<>() {
    };

    public List<PermissionDO> saveAll(List<DirectusPermissions> permissions, String accessToken) throws InternalException {

        Reply result = directus.post(PERMISSIONS_URI, tokenParam(accessToken), composePermissions(permissions));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, PERMISSIONS);
        }
        throw new InternalException("添加权限失败");
    }

    public void removeAll(List<DirectusPermissions> permissions, String accessToken) throws InternalException {

        if (CollectionUtils.isNotEmpty(permissions)) {

            Reply result = directus.delete(PERMISSIONS_URI, tokenParam(accessToken), composePermissionIds(permissions));
            if (!result.isSuccessful()) {

                throw new InternalException("删除权限失败");
            }
        }
    }

    private JsonNode composePermissions(List<DirectusPermissions> permissions) {

        List<PermissionDO> permissionDOs = permissions.stream().map(PermissionDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(permissionDOs);
    }

    private JsonNode composePermissionIds(List<DirectusPermissions> permissions) {

        ArrayNode ids = JsonNodeFactory.instance.arrayNode();
        permissions.forEach(p -> {

            ids.add(p.getId());
        });
        return ids;
    }
}