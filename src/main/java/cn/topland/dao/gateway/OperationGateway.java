package cn.topland.dao.gateway;

import cn.topland.entity.Operation;
import cn.topland.entity.directus.OperationDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationGateway extends BaseGateway {

    @Value("${directus.items.operation}")
    private String OPERATION_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<OperationDO>> OPERATIONS = new TypeReference<>() {
    };

    public OperationDO save(Operation operation, String accessToken) {

        Reply result = directus.post(OPERATION_URI, tokenParam(accessToken), JsonUtils.toJsonNode(OperationDO.from(operation)));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, OperationDO.class);
    }

    public List<OperationDO> saveAll(List<Operation> operations, String accessToken) {

        Reply result = directus.post(OPERATION_URI, tokenParam(accessToken), composeOperations(operations));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, OPERATIONS);
    }

    private JsonNode composeOperations(List<Operation> operations) {

        List<OperationDO> operationDOs = operations.stream().map(OperationDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(operationDOs);
    }
}