package cn.topland.dao.gateway;

import cn.topland.entity.Contract;
import cn.topland.entity.directus.ContractDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContractGateway extends BaseGateway {

    @Value("${directus.items.contract}")
    private String CONTRACT_URI;

    @Autowired
    private DirectusGateway directus;

    public ContractDO save(Contract contract, String accessToken) {

        Reply result = directus.post(CONTRACT_URI, tokenParam(accessToken), JsonUtils.toJsonNode(ContractDO.from(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, ContractDO.class);
    }

    public ContractDO update(Contract contract, String accessToken) {

        Reply result = directus.patch(CONTRACT_URI + "/" + contract.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(ContractDO.from(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, ContractDO.class);
    }

    public ContractDO receivePaper(Contract contract, String accessToken) {

        Reply result = directus.patch(CONTRACT_URI + "/" + contract.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(ContractDO.paper(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, ContractDO.class);
    }

    public ContractDO review(Contract contract, String accessToken) {

        Reply result = directus.patch(CONTRACT_URI + "/" + contract.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(ContractDO.review(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, ContractDO.class);
    }
}