package cn.topland.dao;

import cn.topland.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select dept from Department dept where dept.source=?2 and dept.deptId=?1")
    Department findByDeptId(String deptId, Department.Source source);

    @Query(value = "select dept from Department dept where dept.source=?2 and dept.deptId in ?1")
    List<Department> findByDeptIds(List<String> deptIds, Department.Source source);

    @Query(value = "select new cn.topland.entity.Department(dept.id,dept.deptId,dept.parentDeptId) from Department dept where dept.source=?1")
    List<Department> listAllDeptIds(Department.Source source);

    @Query(value = "select dept from Department dept where dept.source=?1")
    List<Department> findBySource(Department.Source source);
}