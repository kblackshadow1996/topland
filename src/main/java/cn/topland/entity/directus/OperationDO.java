package cn.topland.entity.directus;

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
}