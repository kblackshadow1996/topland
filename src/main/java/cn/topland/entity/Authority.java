package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    /**
     * 权限类型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private AuthorityType type;
}