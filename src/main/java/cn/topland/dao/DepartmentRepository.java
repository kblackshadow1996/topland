package cn.topland.dao;

import cn.topland.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department getDepartmentByDeptId(String deptId);

    List<Department> findAllByDeptIdIn(List<String> deptIds);

    @Query(value = "select new cn.topland.entity.Department(dept.id,dept.deptId,dept.parentDeptId) from Department dept")
    List<Department> listAllDeptIds();
}