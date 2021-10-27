package cn.topland.service;

import cn.topland.dao.DepartmentRepository;
import cn.topland.dao.gateway.DepartmentGateway;
import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.entity.directus.DepartmentDO;
import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.WeworkDepartment;
import cn.topland.service.parser.WeworkDepartmentParser;
import cn.topland.util.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private DepartmentGateway departmentGateway;

    /**
     * 根据组织id同步组织及其直属组织
     */
    public List<DepartmentDO> syncWeworkDept(String deptId, User user) throws InternalException {

        List<Department> departments = departmentParser.parse(filterUpdateDepartments(deptId));
        List<Department> persistDepartments = repository.findByDeptIds(getDeptIds(departments), Source.WEWORK);

        List<Department> mergeDepartments = syncDepartments(persistDepartments, departments, user);
        return departmentGateway.saveAll(mergeDepartments, getParent(deptId), user.getAccessToken());
    }

    /**
     * 同步所有企业微信组织
     */
    public List<DepartmentDO> syncAllWeworkDept(User user) throws InternalException {

        List<Department> departments = departmentParser.parse(weworkGateway.listDepartments(null));
        List<Department> persistDepartments = repository.findBySource(Source.WEWORK);

        List<Department> mergeDepartments = syncDepartments(persistDepartments, departments, user);
        return departmentGateway.saveAll(mergeDepartments, null, user.getAccessToken());
    }

    private Department getParent(String deptId) {

        Department dept = repository.findByDeptIdAndSource(deptId, Source.WEWORK);
        return dept.getParent() != null
                ? repository.findByDeptIdAndSource(dept.getParent().getDeptId(), Source.WEWORK)
                : null;
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
        return setParents(new ArrayList<>(deptMap.values()));
    }

    private List<Department> setParents(List<Department> departments) {

        Map<String, Department> deptMap = departments.stream().collect(Collectors.toMap(Department::getDeptId, d -> d));
        List<String> parents = departments.stream().map(Department::getParentDeptId).collect(Collectors.toList());
        Map<String, Department> parentMap = new HashMap<>();
        parents.forEach(p -> {

            if (deptMap.containsKey(p)) {

                parentMap.put(p, deptMap.get(p));
            }
        });
        departments.forEach(dept -> {

            dept.setParent(parentMap.get(dept.getParentDeptId()));
        });
        return departments;
    }

    private Map<String, Department> getSortedDepartmentByIds(List<Department> persistDepartments) {

        Map<String, Department> deptMap = new LinkedHashMap<>();
        persistDepartments.stream()
                .collect(Collectors.toMap(Department::getDeptId, dept -> dept))
                .entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> deptMap.put(entry.getKey(), entry.getValue()));
        return deptMap;
    }

    private Department createDept(Department dept, User user) {

        dept.setCreator(user);
        dept.setEditor(user);
        return dept;
    }

    private void updateDept(Department persistDept, Department dept) {

        persistDept.setParentDeptId(dept.getParentDeptId());
        persistDept.setName(dept.getName());
        persistDept.setSort(dept.getSort());
        persistDept.setLastUpdateTime(LocalDateTime.now());
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