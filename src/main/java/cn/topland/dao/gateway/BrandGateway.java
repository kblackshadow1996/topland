package cn.topland.dao.gateway;

import cn.topland.entity.Brand;
import cn.topland.entity.directus.BrandDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BrandGateway extends BaseGateway {

    @Value("${directus.items.brand}")
    private String BRAND_URL;

    @Autowired
    private DirectusGateway directus;

    public BrandDO save(Brand brand, String accessToken) throws InternalException {

        Reply result = directus.post(BRAND_URL, tokenParam(accessToken), JsonUtils.toJsonNode(BrandDO.from(brand)));
        System.out.println(result.getContent());
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, BrandDO.class);
        }
        throw new InternalException("创建品牌失败");
    }

    public BrandDO update(Brand brand, String accessToken) throws InternalException {

        Reply result = directus.patch(BRAND_URL + "/" + brand.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(BrandDO.from(brand)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, BrandDO.class);
        }
        throw new InternalException("更新品牌失败");
    }
}