package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 功能
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class Authority extends SimpleIdEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 组装directus_permissions的字段对象
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority")
    List<Permission> permissions;
}