package cn.topland.dao;

import cn.topland.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select dept from Department dept where dept.source=?2 and dept.deptId in ?1")
    List<Department> findByDeptIds(List<String> deptIds, Department.Source source);

    @Query(value = "select dept from Department dept where dept.source=?1")
    List<Department> listAllDeptIds(Department.Source source);

    @Query(value = "select dept from Department dept where dept.source=?1")
    List<Department> findBySource(Department.Source source);

    Department findByDeptIdAndSource(String deptId, Department.Source source);

    boolean existsByDeptIdAndSource(String deptId, Department.Source source);
}