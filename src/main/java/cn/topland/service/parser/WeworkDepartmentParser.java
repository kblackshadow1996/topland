package cn.topland.service.parser;

import cn.topland.entity.Department;
import cn.topland.gateway.response.WeworkDepartment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static cn.topland.entity.Department.Source;
import static cn.topland.entity.Department.Type;

@Component
public class WeworkDepartmentParser {

    public List<Department> parse(List<WeworkDepartment> weworkDepartments) {

        return weworkDepartments.stream().map(this::parse).collect(Collectors.toList());
    }

    public Department parse(WeworkDepartment dept) {

        Department department = new Department();
        department.setName(dept.getName());
        department.setParentDeptId(dept.getParentId());
        department.setDeptId(dept.getId());
        department.setSource(Source.WEWORK);
        department.setType(getType(dept.getParentId()));
        department.setSort(Long.valueOf(dept.getOrder()));
        return department;
    }

    private Type getType(String parentId) {

        return StringUtils.equals("0", parentId)
                ? Type.BRANCH
                : Type.DEPARTMENT;
    }
}