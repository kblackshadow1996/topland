package cn.topland.dao.gateway;

import cn.topland.entity.PackageService;
import cn.topland.entity.directus.PackageServiceDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackageServiceGateway extends BaseGateway {

    @Value("${directus.items.package_service}")
    private String PACKAGE_SERVICE_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<PackageServiceDO>> SERVICES = new TypeReference<>() {
    };

    public List<PackageServiceDO> saveAll(List<PackageService> services, String accessToken) throws InternalException {

        List<PackageServiceDO> packageServices = createAll(getCreatePackageServices(services), accessToken);
        packageServices.addAll(updateAll(getUpdatePackageServices(services), accessToken));
        return packageServices;
    }

    private List<PackageServiceDO> createAll(List<PackageService> services, String accessToken) throws InternalException {

        Reply result = directus.post(PACKAGE_SERVICE_URI, tokenParam(accessToken), composePackageServices(services));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, SERVICES);
        }
        throw new InternalException("新增联系人失败");
    }

    private List<PackageServiceDO> updateAll(List<PackageService> services, String accessToken) throws InternalException {

        List<PackageServiceDO> packageServices = new ArrayList<>();
        for (PackageService service : services) {

            Reply result = directus.patch(PACKAGE_SERVICE_URI + "/" + service.getId(), tokenParam(accessToken),
                    JsonUtils.toJsonNode(PackageServiceDO.from(service)));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                packageServices.add(JsonUtils.parse(data, PackageServiceDO.class));
            } else {

                throw new InternalException("更新套餐服务异常");
            }
        }
        return packageServices;
    }

    private List<PackageService> getCreatePackageServices(List<PackageService> services) {

        return services.stream().filter(service -> service.getId() == null).collect(Collectors.toList());
    }

    private List<PackageService> getUpdatePackageServices(List<PackageService> services) {

        return services.stream().filter(service -> service.getId() != null).collect(Collectors.toList());
    }

    private JsonNode composePackageServices(List<PackageService> services) {

        List<PackageServiceDO> packageServiceDOs = services.stream().map(PackageServiceDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(packageServiceDOs);
    }
}