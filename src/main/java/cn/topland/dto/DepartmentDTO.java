package cn.topland.dto;

import cn.topland.entity.Department;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DepartmentDTO implements Serializable {

    private Long id;

    private String name;

    private String deptId;

    private String parentDeptId;

    private Long sort;

    private Department.Type type;

    private Department.Source source;
}