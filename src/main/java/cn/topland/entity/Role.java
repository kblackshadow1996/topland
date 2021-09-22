package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 角色，与directus中角色一对一
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role extends RecordEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 顶层用户角色
     */
    @ManyToMany
    @JoinTable(name = "user_role_roles",
            joinColumns = {@JoinColumn(name = "user_role")}, inverseJoinColumns = {@JoinColumn(name = "role")})
    private List<Role> roles;

    /**
     * directus角色
     */
    @OneToOne
    @JoinColumn(name = "directus_role")
    private DirectusRoles directusRole;

    @ManyToMany
    @JoinTable(name = "role_authority_type",
            joinColumns = {@JoinColumn(name = "role")}, inverseJoinColumns = {@JoinColumn(name = "authority_type")})
    private List<AuthorityType> authType;
}