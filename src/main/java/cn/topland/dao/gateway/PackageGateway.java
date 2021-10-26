package cn.topland.dao.gateway;

import cn.topland.entity.Package;
import cn.topland.entity.directus.PackageDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        Reply result = directus.post(PACKAGE_URI, tokenParam(accessToken), composePackage(pkg));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, PackageDO.class);
        }
        throw new InternalException("新增产品套餐失败");
    }

    public PackageDO update(Package pkg, String accessToken) throws InternalException {

        Reply result = directus.patch(PACKAGE_URI + "/" + pkg.getId(), tokenParam(accessToken), composePackage(pkg));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, PackageDO.class);
        }
        throw new InternalException("更新产品套餐失败");
    }

    private JsonNode composePackage(Package pkg) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", pkg.getName());
        node.put("remark", pkg.getRemark());
        node.put("creator", pkg.getCreator().getId());
        node.put("editor", pkg.getEditor().getId());
        node.put("create_time", FORMATTER.format(pkg.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(pkg.getLastUpdateTime()));
        return node;
    }
}