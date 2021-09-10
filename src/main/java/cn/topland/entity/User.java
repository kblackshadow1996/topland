package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends RecordEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 唯一标识id
     */
    @Column(unique = true)
    private String userId;

    /**
     * 职称
     */
    private String position;

    /**
     * 部门
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_departments",
            joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "department_id")})
    private List<Department> departments;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 负责组织
     */
    private String leadDepartments;

    /**
     * 启用状态
     */
    private Boolean active;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色
     */
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles = new ArrayList<>();

    /**
     * 数据权限
     */
    @Enumerated(EnumType.STRING)
    private DataAuth auth;

    public List<String> roles() {

        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public List<String> authorities() {

        List<String> authorities = new ArrayList<>();
        roles.forEach(role -> {

            List<String> roleAuths = role.getAuths().stream().map(Authority::getName).collect(Collectors.toList());
            authorities.addAll(roleAuths);
        });
        return authorities.stream().distinct().collect(Collectors.toList());
    }

    public enum DataAuth {

        USER, // 用户
        DEPARTMENT, // 部门
        DEPARTMENT_AND_CHILDREN, // 部门及其子部门
        ALL // 所有
    }
}