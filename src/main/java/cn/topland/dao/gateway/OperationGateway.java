package cn.topland.dao.gateway;

import cn.topland.entity.Operation;
import cn.topland.entity.directus.OperationDO;
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
public class OperationGateway extends BaseGateway {

    @Value("${directus.items.operation}")
    private String OPERATION_URI;

    @Autowired
    private DirectusGateway directus;

    public OperationDO save(Operation operation, String accessToken) throws InternalException {

        Reply result = directus.post(OPERATION_URI, tokenParam(accessToken), composeOperation(operation));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, OperationDO.class);
        }
        throw new InternalException("添加操作记录失败");
    }

    private JsonNode composeOperation(Operation operation) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("module", operation.getModule().name());
        node.put("module_id", operation.getModuleId());
        node.put("action", operation.getAction());
        node.put("remark", operation.getRemark());
        node.put("creator", operation.getCreator().getId());
        node.put("editor", operation.getEditor().getId());
        node.put("create_time", FORMATTER.format(operation.getCreateTime()));
        node.put("last_update_time", FORMATTER.format(operation.getLastUpdateTime()));
        return node;
    }
}