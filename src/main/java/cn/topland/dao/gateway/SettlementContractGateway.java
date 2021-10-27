package cn.topland.dao.gateway;

import cn.topland.entity.SettlementContract;
import cn.topland.entity.directus.SettlementContractDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SettlementContractGateway extends BaseGateway {

    @Value("${directus.items.settlement_contract}")
    private String SETTLEMENT_URI;

    @Autowired
    private DirectusGateway directus;

    public SettlementContractDO save(SettlementContract contract, String accessToken) throws InternalException {

        Reply result = directus.post(SETTLEMENT_URI, tokenParam(accessToken), JsonUtils.toJsonNode(SettlementContractDO.from(contract)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, SettlementContractDO.class);
        }
        throw new InternalException("创建结算合同失败");
    }

    public SettlementContractDO update(SettlementContract contract, String accessToken) throws InternalException {

        Reply result = directus.patch(SETTLEMENT_URI + "/" + contract.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(SettlementContractDO.from(contract)));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, SettlementContractDO.class);
        }
        throw new InternalException("更新结算合同失败");
    }
}