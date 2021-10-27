package cn.topland.dao.gateway;

import cn.topland.entity.Package;
import cn.topland.entity.directus.PackageDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PackageGateway extends BaseGateway {

    @Value("${directus.items.package}")
    private String PACKAGE_URI;

    @Autowired
    private DirectusGateway directus;

    public PackageDO save(Package pkg, String accessToken) {

        Reply result = directus.post(PACKAGE_URI, tokenParam(accessToken), JsonUtils.toJsonNode(PackageDO.from(pkg)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, PackageDO.class);
    }

    public PackageDO update(Package pkg, String accessToken) {

        Reply result = directus.patch(PACKAGE_URI + "/" + pkg.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(PackageDO.from(pkg)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, PackageDO.class);
    }
}