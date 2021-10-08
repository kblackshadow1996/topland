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

    /**
     * directus角色
     */
    @OneToOne
    @JoinColumn(name = "directus_role")
    private DirectusRoles role;

    /**
     * 功能
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
            joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private List<Authority> authorities;

    /**
     * 备注
     */
    private String remark;
}