package cn.topland.dto.converter;

import cn.topland.dto.DepartmentDTO;
import cn.topland.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentConverter {

    public List<DepartmentDTO> toDepartmentDTOs(List<Department> departments) {

        return departments.stream().map(this::toDepartmentDTO).collect(Collectors.toList());
    }

    private DepartmentDTO toDepartmentDTO(Department department) {

        return department != null
                ? composeDepartmentDTO(department)
                : null;
    }

    private DepartmentDTO composeDepartmentDTO(Department department) {

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setSort(department.getSort());
        dto.setDeptId(department.getDeptId());
        dto.setParentDeptId(department.getParentDeptId());
        dto.setType(department.getType());
        dto.setSource(department.getSource());
        return dto;
    }
}