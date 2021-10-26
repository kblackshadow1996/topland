package cn.topland.dao.gateway;

import cn.topland.entity.Department;
import cn.topland.entity.directus.DepartmentDO;
import cn.topland.entity.directus.DirectusIdEntity;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DepartmentGateway extends BaseGateway {

    @Value("${directus.items.department}")
    private String DEPARTMENT_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<DepartmentDO>> DEPARTMENTS = new TypeReference<>() {
    };

    public List<DepartmentDO> saveAll(List<Department> departments, Department parent, String accessToken) throws InternalException {

        List<DepartmentDO> departmentDOs = addDepartments(getCreateDepartments(departments), accessToken);
        departmentDOs.addAll(toDOs(getUpdateDepartments(departments)));
        mappingParentDept(departmentDOs, getParent(departments), parent);
        return updateWithParent(departmentDOs, accessToken);
    }

    private List<DepartmentDO> updateWithParent(List<DepartmentDO> departmentDOs, String accessToken) throws InternalException {

        List<DepartmentDO> departments = new ArrayList<>();
        for (DepartmentDO dept : departmentDOs) {

            Reply result = directus.patch(DEPARTMENT_URI + "/" + dept.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(dept));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                departments.add(JsonUtils.parse(data, DepartmentDO.class));
            } else {

                throw new InternalException("同步更新部门错误");
            }
        }
        return departments;
    }

    private Map<String, String> getParent(List<Department> departments) {

        Map<String, String> parentMap = new HashMap<>();
        for (Department department : departments) {

            parentMap.put(department.getDeptId(), department.getParentDeptId());
        }
        return parentMap;
    }

    private List<DepartmentDO> toDOs(List<Department> departments) {

        return departments.stream().map(DepartmentDO::from).collect(Collectors.toList());
    }

    private void mappingParentDept(List<DepartmentDO> deptDOs, Map<String, String> parentMap, Department parentDept) {

        // 得到部门deptId与其父部门id的关系映射
        Map<String, Long> deptMap = deptDOs.stream().collect(Collectors.toMap(DepartmentDO::getDeptId, DirectusIdEntity::getId));
        if (parentDept != null) {

            deptMap.put(parentDept.getDeptId(), parentDept.getId());
        }
        Map<String, Long> parentDeptMap = new HashMap<>();
        parentMap.forEach((dept, parent) -> {

            parentDeptMap.put(dept, deptMap.get(parent));
        });
        deptDOs.forEach(dept -> {

            dept.setParent(parentDeptMap.get(dept.getDeptId()));
        });
    }

    private List<DepartmentDO> addDepartments(List<Department> departments, String accessToken) throws InternalException {

        List<DepartmentDO> dos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(departments)) {

            Reply result = directus.post(DEPARTMENT_URI, tokenParam(accessToken), composeDepartments(departments));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                dos = JsonUtils.parse(data, DEPARTMENTS);
            } else {

                throw new InternalException("同步新增部门错误");
            }
        }
        return dos;
    }

    private List<Department> getCreateDepartments(List<Department> departments) {

        return departments.stream().filter(dept -> dept.getId() == null).collect(Collectors.toList());
    }

    private List<Department> getUpdateDepartments(List<Department> departments) {

        return departments.stream().filter(dept -> dept.getId() != null).collect(Collectors.toList());
    }

    private JsonNode composeDepartments(List<Department> departments) {

        List<DepartmentDO> departmentDOs = departments.stream().map(DepartmentDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(departmentDOs);
    }
}