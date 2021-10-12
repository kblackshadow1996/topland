package cn.topland.dto;

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
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 外部职位
     */
    private String externalPosition;

    /**
     * 内部职位
     */
    private String internalPosition;

    /**
     * 部门
     */
    private List<DepartmentDTO> departments;

    /**
     * 部门负责人
     */
    private String leadDepartments;

    /**
     * 来源
     */
    private Source source;

    /**
     * 工号
     */
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
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * directus user id
     */
    private String directusUserId;

    /**
     * directus email
     */
    private String directusEmail;

    /**
     * directus 密码
     */
    private String directusPassword;
}