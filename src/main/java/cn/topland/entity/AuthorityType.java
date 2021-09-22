package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 权限
 */
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
     * 组装directus_permissions的字段对象
     */
    @OneToMany
    @JoinColumn(name = "type")
    List<Authority> authorities;
}