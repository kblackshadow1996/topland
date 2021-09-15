package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Source source;

    public enum Source {

        WEWORK,
        OTHER
    }
}