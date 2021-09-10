package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
     * 备注
     */
    private String remark;

    /**
     * 权限
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "roles_authorities",
            joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Authority> auths = new ArrayList<>();
}