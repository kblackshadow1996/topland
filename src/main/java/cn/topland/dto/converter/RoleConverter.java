package cn.topland.dto.converter;

import cn.topland.dto.RoleDTO;
import cn.topland.entity.directus.RoleDO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends BaseConverter<RoleDO, RoleDTO> {

    @Override
    public RoleDTO toDTO(RoleDO role) {

        return role != null
                ? composeRole(role)
                : null;
    }

    private RoleDTO composeRole(RoleDO role) {

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDirectusRole(role.getDirectusRole());
        dto.setRemark(role.getRemark());
        dto.setCreator(role.getCreator());
        dto.setEditor(role.getEditor());
        dto.setCreateTime(role.getCreateTime());
        dto.setLastUpdateTime(role.getLastUpdateTime());
        dto.setAuthorities(role.getAuthorities());
        return dto;
    }
}