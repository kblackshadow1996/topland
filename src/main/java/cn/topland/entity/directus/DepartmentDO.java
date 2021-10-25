package cn.topland.entity.directus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentDO extends DirectusRecordEntity {

    private String name;

    @JsonProperty(value = "dept_id")
    private String deptId;

    private Long parent;

    private Long sort;

    private String type;

    private String source;

    public JsonNode toJson() {

        ObjectNode dept = JsonNodeFactory.instance.objectNode();
        dept.put("name", name);
        dept.put("dept_id", deptId);
        dept.put("parent", parent);
        dept.put("sort", sort);
        dept.put("type", type);
        dept.put("source", source);
        dept.put("creator", creator);
        dept.put("editor", editor);
        dept.put("create_time", DATETIME_FORMATTER.format(createTime));
        dept.put("last_update_time", DATETIME_FORMATTER.format(lastUpdateTime));
        return dept;
    }
}