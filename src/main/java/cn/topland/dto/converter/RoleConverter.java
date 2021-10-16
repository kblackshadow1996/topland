package cn.topland.dto.converter;

import cn.topland.dto.RoleDTO;
import cn.topland.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends BaseConverter<Role, RoleDTO> {

    @Override
    public RoleDTO toDTO(Role role) {

        return role != null
                ? composeRole(role)
                : null;
    }

    private RoleDTO composeRole(Role role) {

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setRole(getId(role.getRole()));
        dto.setRemark(role.getRemark());
        dto.setCreator(getId(role.getCreator()));
        dto.setCreateTime(role.getCreateTime());
        return dto;
    }
}