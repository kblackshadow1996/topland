package cn.topland.dao.gateway;

import cn.topland.entity.SettlementContract;
import cn.topland.entity.directus.SettlementContractDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SettlementContractGateway extends BaseGateway {

    @Value("${directus.items.settlement_contract}")
    private String SETTLEMENT_URI;

    @Autowired
    private DirectusGateway directus;

    public SettlementContractDO save(SettlementContract contract, String accessToken) {

        Reply result = directus.post(SETTLEMENT_URI, tokenParam(accessToken), JsonUtils.toJsonNode(SettlementContractDO.from(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, SettlementContractDO.class);
    }

    public SettlementContractDO update(SettlementContract contract, String accessToken) {

        Reply result = directus.patch(SETTLEMENT_URI + "/" + contract.getId(), tokenParam(accessToken),
                JsonUtils.toJsonNode(SettlementContractDO.from(contract)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, SettlementContractDO.class);
    }
}