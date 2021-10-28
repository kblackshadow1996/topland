package cn.topland.vo;

import cn.topland.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UserVO implements Serializable {

    /**
     * 用户ID
     */
    private List<Long> users;

    /**
     * 角色ID
     */
    private Long role;

    /**
     * 数据权限
     */
    private User.DataAuth auth;

    /**
     * 创建人ID
     */
    private Long creator;
}