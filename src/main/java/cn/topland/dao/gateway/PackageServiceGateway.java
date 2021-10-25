package cn.topland.dao.gateway;

import cn.topland.entity.PackageService;
import cn.topland.entity.directus.PackageServiceDO;
import cn.topland.util.DirectusGateway;
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

import java.util.List;

@Component
public class PackageServiceGateway extends BaseGateway {

    @Value("${directus.items.package_service}")
    private String PACKAGE_SERVICE_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<PackageServiceDO>> SERVICES = new TypeReference<>() {
    };

    public List<PackageServiceDO> saveAll(List<PackageService> services, String accessToken) throws InternalException {

        Reply result = directus.post(PACKAGE_SERVICE_URI, tokenParam(accessToken), composePackageServices(services));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, SERVICES);
        }
        throw new InternalException("新增联系人失败");
    }


    public List<PackageServiceDO> updateAll(List<PackageService> services, String token) {
        return null;
    }

    private JsonNode composePackageServices(List<PackageService> services) {

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        services.forEach(service -> {

            array.add(composePackageService(service));
        });
        return array;
    }

    private JsonNode composePackageService(PackageService service) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("service", service.getService().getId());
        node.put("unit", service.getUnit());
        node.put("delivery", service.getDelivery());
        node.put("price", service.getPrice());
        node.put("package", service.getPkgId());
        node.put("create_time", FORMATTER.format(service.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(service.getLastUpdateTime()));
        return node;
    }
}