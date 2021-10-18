package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

/**
 * 用户
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends RecordEntity {

    private static final String EMAIL_HOST = "@topland.cn";

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
     * 职称(外部职称)
     */
    private String externalPosition;

    /**
     * 头衔(内部职称)
     */
    private String internalPosition;

    /**
     * 部门
     */
    @ManyToMany
    @JoinTable(name = "user_department",
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

    @OneToOne
    @JoinColumn(name = "directus_user")
    private DirectusUsers directusUser;

    /**
     * directus邮箱
     */
    private String directusEmail;
    /**
     * directus登录密码，directus_users中已经将密码加密，
     * 存明文密码方便登录
     */
    private String directusPassword;

    @Enumerated(EnumType.STRING)
    private DataAuth auth;

    @OneToOne
    @JoinColumn(name = "role")
    private Role role;

    // 根据用户来源，用户三方id及固定邮箱域名创建如"wework_weekend@topland.cn"
    public String generateEmail() {

        return source.name().toLowerCase(Locale.ROOT) + "_" + userId + EMAIL_HOST;
    }

    public enum Source {

        WEWORK,
        OTHER
    }

    public enum DataAuth {

        ACCOUNT,
        ALL
    }
}