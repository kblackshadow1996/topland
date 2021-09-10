package cn.topland.service;

import cn.topland.dao.DepartmentRepository;
import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.WeworkDepartment;
import cn.topland.service.parser.DepartmentParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private WeworkGateway weworkGateway;

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private DepartmentParser departmentParser;

    public List<Department> listAll() {

        return repository.findAll();
    }

    /**
     * 根据组织id同步组织及其直属组织
     */
    @Transactional
    public List<Department> sync(String deptId, User creator) {

        log.info("sync departments of dept {} by {} start...", deptId, creator.getName());
        List<Department> departments = departmentParser.parse(filterUpdateDepartments(deptId));
        List<Department> persistDepartments = repository.findAllByDeptIdIn(getDeptIds(departments));
        List<Department> newDepartments = syncDepartments(persistDepartments, departments, creator);

        return repository.saveAllAndFlush(newDepartments);
    }

    /**
     * 同步所有组织
     */
    @Transactional
    public List<Department> syncAll(User creator) {

        log.info("sync all departments by {} start...", creator.getName());
        List<Department> departments = departmentParser.parse(weworkGateway.listDepartments(null));
        List<Department> persistDepartments = repository.findAll();
        List<Department> newDepartments = syncDepartments(persistDepartments, departments, creator);

        return repository.saveAllAndFlush(newDepartments);
    }

    private List<Department> syncDepartments(List<Department> persistDepartments, List<Department> departments, User user) {

        Map<String, Department> deptMap = persistDepartments.stream()
                .collect(Collectors.toMap(Department::getDeptId, dept -> dept));
        departments.forEach(dept -> {

            if (deptMap.containsKey(dept.getDeptId())) {

                updateDept(deptMap.get(dept.getDeptId()), dept);

            } else {

                deptMap.put(dept.getDeptId(), createDept(dept, user));
            }
        });
        return new ArrayList<>(deptMap.values());
    }

    private void updateDept(Department persistDept, Department dept) {

        persistDept.setName(dept.getName());
        persistDept.setSort(dept.getSort());
    }

    private Department createDept(Department dept, User user) {

        dept.setCreatorId(user.getUserId());
        dept.setCreator(user.getName());
        dept.setEditorId(user.getUserId());
        dept.setEditor(user.getName());
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