package cn.topland.dao.gateway;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.directus.PermissionDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
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

    public List<PermissionDO> saveAll(List<DirectusPermissions> permissions, String accessToken) {

        Reply result = directus.post(PERMISSIONS_URI, tokenParam(accessToken), composePermissions(permissions));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, PERMISSIONS);
    }

    public void removeAll(List<DirectusPermissions> permissions, String accessToken) {

        if (CollectionUtils.isNotEmpty(permissions)) {

            directus.delete(PERMISSIONS_URI, tokenParam(accessToken), composePermissionIds(permissions));
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