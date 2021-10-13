package cn.topland.dto.converter;

import cn.topland.dto.DepartmentDTO;
import cn.topland.entity.Department;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentConverter extends BaseConverter<Department, DepartmentDTO> {

    @Override
    public List<DepartmentDTO> toDTOs(List<Department> departments) {

        return CollectionUtils.isEmpty(departments)
                ? List.of()
                : departments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO toDTO(Department department) {

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
        dto.setCreator(getUserId(department.getCreator()));
        dto.setCreateTime(department.getCreateTime());
        return dto;
    }
}