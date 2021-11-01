package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class UserDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 三方ID
     */
    @JsonProperty(value = "user_id")
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 外部职位
     */
    @JsonProperty(value = "external_position")
    private String externalPosition;

    /**
     * 内部职位
     */
    @JsonProperty(value = "internal_position")
    private String internalPosition;

    /**
     * 用户-部门中间表ID
     */
    private List<Long> departments;

    /**
     * 负责部门
     */
    @JsonProperty(value = "lead_departments")
    private String leadDepartments;

    /**
     * 来源
     */
    private String source;

    /**
     * 工号
     */
    @JsonProperty(value = "employee_id")
    private String employeeId;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 激活状态
     */
    private Boolean active;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色ID
     */
    private Long role;

    /**
     * 数据权限
     */
    private String auth;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 修改人ID
     */
    private Long editor;

    /**
     * 创建时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;

//    @JsonProperty(value = "directus_user")
//    private String directusUser;
//
//    @JsonProperty(value = "directus_email")
//    private String directusEmail;
//
//    @JsonProperty(value = "directus_password")
//    private String directusPassword;

    /**
     * 口令
     */
    @JsonProperty(value = "access_token")
    private String accessToken;

//    @JsonProperty(value = "refresh_token")
//    private String refreshToken;
}