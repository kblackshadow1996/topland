package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "authority_type")
public class AuthorityType extends IdEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 权限
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type", cascade = CascadeType.ALL)
    @OrderBy(value = "id")
    private List<Authority> authorities;
}