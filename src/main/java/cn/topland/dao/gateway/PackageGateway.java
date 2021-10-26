package cn.topland.dao.gateway;

import cn.topland.entity.Package;
import cn.topland.entity.directus.PackageDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PackageGateway extends BaseGateway {

    @Value("${directus.items.package}")
    private String PACKAGE_URI;

    @Autowired
    private DirectusGateway directus;

    public PackageDO save(Package pkg, String accessToken) throws InternalException {

        Reply result = directus.post(PACKAGE_URI, tokenParam(accessToken), JsonUtils.toJsonNode(PackageDO.from(pkg)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, PackageDO.class);
        }
        throw new InternalException("新增产品套餐失败");
    }

    public PackageDO update(Package pkg, String accessToken) throws InternalException {

        Reply result = directus.patch(PACKAGE_URI + "/" + pkg.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(PackageDO.from(pkg)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, PackageDO.class);
        }
        throw new InternalException("更新产品套餐失败");
    }
}