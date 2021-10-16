package cn.topland.vo;

import cn.topland.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UserVO implements Serializable {

    private List<Long> users;

    private Long role;

    private User.DataAuth auth;

    private Long creator;
}