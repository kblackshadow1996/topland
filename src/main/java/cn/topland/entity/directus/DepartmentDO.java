package cn.topland.entity.directus;

import cn.topland.entity.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public static DepartmentDO from(Department department) {

        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setId(department.getId());
        departmentDO.setName(department.getName());
        departmentDO.setDeptId(department.getDeptId());
        departmentDO.setParent(department.getParent() == null ? null : department.getParent().getId());
        departmentDO.setSort(department.getSort());
        departmentDO.setType(department.getType().name());
        departmentDO.setSource(department.getSource().name());
        departmentDO.setCreator(department.getCreator().getId());
        departmentDO.setEditor(department.getEditor().getId());
        departmentDO.setCreateTime(department.getCreateTime());
        departmentDO.setLastUpdateTime(department.getLastUpdateTime());
        return departmentDO;
    }
}