package cn.topland.entity.directus;

import cn.topland.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleDO extends DirectusRecordEntity {

    private String name;

    @JsonProperty(value = "directus_role")
    private String directusRole;

    private List<Long> authorities;

    private String remark;

    public static RoleDO from(Role role) {

        RoleDO roleDO = new RoleDO();
        roleDO.setName(role.getName());
        roleDO.setDirectusRole(role.getRole() == null ? null : role.getRole().getId());
        roleDO.setRemark(role.getRemark());
        roleDO.setCreator(role.getCreator().getId());
        roleDO.setEditor(role.getEditor().getId());
        roleDO.setCreateTime(role.getCreateTime());
        roleDO.setLastUpdateTime(role.getLastUpdateTime());
        return roleDO;
    }
}