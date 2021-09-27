package cn.topland.service;

import cn.topland.dao.DepartmentRepository;
import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.WeworkDepartment;
import cn.topland.service.parser.WeworkDepartmentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.topland.entity.Department.Source;

@Service
public class DepartmentService {

    @Autowired
    private WeworkGateway weworkGateway;

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private WeworkDepartmentParser departmentParser;

    /**
     * 根据组织id同步组织及其直属组织
     */
    @Transactional
    public List<Department> syncWeworkDept(String deptId, User creator) {

        List<Department> departments = departmentParser.parse(filterUpdateDepartments(deptId));
        List<Department> persistDepartments = repository.findByDeptIds(getDeptIds(departments), Source.WEWORK);
        List<Department> newDepartments = syncDepartments(persistDepartments, departments, creator);

        return repository.saveAllAndFlush(newDepartments);
    }

    /**
     * 同步所有企业微信组织
     */
    @Transactional
    public List<Department> syncAllWeworkDept(User creator) {

        List<Department> departments = departmentParser.parse(weworkGateway.listDepartments(null));
        List<Department> persistDepartments = repository.findBySource(Source.WEWORK);
        List<Department> newDepartments = syncDepartments(persistDepartments, departments, creator);

        return repository.saveAllAndFlush(newDepartments);
    }

    private List<Department> syncDepartments(List<Department> persistDepartments, List<Department> departments, User user) {

        Map<String, Department> deptMap = getSortedDepartmentByIds(persistDepartments);
        departments.forEach(dept -> {

            if (deptMap.containsKey(dept.getDeptId())) {

                updateDept(deptMap.get(dept.getDeptId()), dept);

            } else {

                deptMap.put(dept.getDeptId(), createDept(dept, user));
            }
        });
        return new ArrayList<>(deptMap.values());
    }

    private Map<String, Department> getSortedDepartmentByIds(List<Department> persistDepartments) {

        Map<String, Department> deptMap = new LinkedHashMap<>();
        persistDepartments.stream()
                .collect(Collectors.toMap(Department::getDeptId, dept -> dept))
                .entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> deptMap.put(entry.getKey(), entry.getValue()));
        return deptMap;
    }

    private void updateDept(Department persistDept, Department dept) {

        persistDept.setName(dept.getName());
        persistDept.setSort(dept.getSort());
    }

    private Department createDept(Department dept, User user) {

        dept.setCreatorId(user.getId());
        dept.setEditorId(user.getId());
        return dept;
    }

    private List<String> getDeptIds(List<Department> departments) {

        return departments.stream().map(Department::getDeptId).collect(Collectors.toList());
    }

    // 指定组织id则同步该组织以及该组织直属部门
    private List<WeworkDepartment> filterUpdateDepartments(String deptId) {

        return weworkGateway.listDepartments(deptId).stream()
                .filter(dept -> deptId.equals(dept.getId()) || deptId.equals(dept.getParentId()))
                .collect(Collectors.toList());
    }
}