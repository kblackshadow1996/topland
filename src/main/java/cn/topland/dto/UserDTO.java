package cn.topland.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO implements Serializable {

    /**
     * 账号
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

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
    private String createTime;

    /**
     * 创建人
     */
    private String creator;
}