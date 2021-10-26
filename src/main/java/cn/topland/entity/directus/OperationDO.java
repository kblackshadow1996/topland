package cn.topland.entity.directus;

import cn.topland.entity.Operation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperationDO extends DirectusRecordEntity {

    private String module;

    @JsonProperty(value = "module_id")
    private String moduleId;

    private String action;

    private String remark;

    public static OperationDO from(Operation operation) {

        OperationDO operationDO = new OperationDO();
        operationDO.setModule(operation.getModule().name());
        operationDO.setModuleId(operation.getModuleId());
        operationDO.setAction(operation.getAction());
        operationDO.setRemark(operation.getRemark());
        operationDO.setCreator(operation.getCreator().getId());
        operationDO.setEditor(operation.getEditor().getId());
        operationDO.setCreateTime(operation.getCreateTime());
        operationDO.setLastUpdateTime(operation.getLastUpdateTime());
        return operationDO;
    }
}