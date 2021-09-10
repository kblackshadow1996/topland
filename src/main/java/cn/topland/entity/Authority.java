package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class Authority extends IdEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 展示名称
     */
    private String displayName;
}