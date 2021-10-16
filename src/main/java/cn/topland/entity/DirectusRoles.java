package cn.topland.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * directus角色名称
 */
@Setter
@Getter
@Entity
@Table(name = "directus_roles")
public class DirectusRoles extends UuidEntity {

    private String name;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "role")
    private List<DirectusPermissions> permissions;
}