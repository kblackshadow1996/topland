package cn.topland.entity.directus;

import cn.topland.entity.DirectusRoles;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RolesDO extends DirectusUuidEntity {

    private String name;

    public static RolesDO from(DirectusRoles roles) {

        RolesDO rolesDO = new RolesDO();
        rolesDO.setName(roles.getName());
        return rolesDO;
    }
}