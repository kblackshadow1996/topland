package cn.topland.dto.converter;

import cn.topland.dto.RoleDTO;
import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        dto.setAuthorities(listAuthIds(role.getAuthorities()));
        return dto;
    }

    private List<Long> listAuthIds(List<Authority> authorities) {

        return CollectionUtils.isEmpty(authorities)
                ? List.of()
                : authorities.stream().map(this::getId).collect(Collectors.toList());
    }
}