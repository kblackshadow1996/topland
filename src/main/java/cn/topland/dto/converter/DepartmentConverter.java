package cn.topland.dto.converter;

import cn.topland.dto.DepartmentDTO;
import cn.topland.entity.directus.DepartmentDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentConverter extends BaseConverter<DepartmentDO, DepartmentDTO> {

    @Override
    public List<DepartmentDTO> toDTOs(List<DepartmentDO> departments) {

        return CollectionUtils.isEmpty(departments)
                ? List.of()
                : departments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO toDTO(DepartmentDO department) {

        return department != null
                ? composeDepartmentDTO(department)
                : null;
    }

    private DepartmentDTO composeDepartmentDTO(DepartmentDO department) {

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setSort(department.getSort());
        dto.setDeptId(department.getDeptId());
        dto.setParent(department.getParent());
        dto.setType(department.getType());
        dto.setSource(department.getSource());
        dto.setCreator(department.getCreator());
        dto.setEditor(department.getEditor());
        dto.setCreateTime(department.getCreateTime());
        dto.setLastUpdateTime(department.getLastUpdateTime());
        return dto;
    }
}