package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static cn.topland.entity.User.Source;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
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
     * 部门
     */
    private List<DepartmentDTO> departments;

    /**
     * 部门负责人
     */
    @JsonProperty(value = "lead_departments")
    private String leadDepartments;

    /**
     * 来源
     */
    private Source source;

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
     * 账号状态
     */
    private boolean active;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * directus user id
     */
    @JsonProperty(value = "directus_user_id")
    private String directusUserId;

    /**
     * directus email
     */
    @JsonProperty(value = "directus_email")
    private String directusEmail;

    /**
     * directus 密码
     */
    @JsonProperty(value = "directus_password")
    private String directusPassword;
}