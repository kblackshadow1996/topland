package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 角色(扩展directus_roles表，记录创建人、创建时间等)
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role extends RecordEntity {

    @OneToOne
    @JoinColumn(name = "directus_role")
    private DirectusRoles role;

    @ManyToMany
    @JoinTable(name = "roles_authorities",
            joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private List<Authority> authorities;
}