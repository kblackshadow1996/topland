package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
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

    @OneToOne
    @JoinColumn(name = "directus_user")
    private DirectusUsers directusUser;

    /**
     * directus登录密码，directus_users中已经将密码加密，
     * 存明文密码方便登录
     */
    private String directusPassword = getRandomPassword();

    @Enumerated(EnumType.STRING)
    private DataAuth auth;

    private String getRandomPassword() {

        return RandomStringUtils.randomAlphanumeric(8);
    }

    public String getDirectusId() {

        return Objects.isNull(directusUser)
                ? null
                : directusUser.getId();
    }

    public String getDirectusEmail() {

        return Objects.isNull(directusUser)
                ? generateEmail()
                : directusUser.getEmail();
    }

    // 根据用户来源，用户三方id及固定邮箱域名创建如"wework_weekend@topland.cn"
    private String generateEmail() {

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