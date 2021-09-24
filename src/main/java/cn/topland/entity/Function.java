package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "function")
public class Function extends SimpleIdEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 组装directus_permissions的字段对象
     */
    @ManyToMany
    @JoinTable(name = "function_authority",
            joinColumns = {@JoinColumn(name = "func")}, inverseJoinColumns = {@JoinColumn(name = "auth")})
    List<Authority> authorities;
}