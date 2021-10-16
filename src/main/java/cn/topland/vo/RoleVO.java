package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class RoleVO implements Serializable {

    private String name;

    private String remark;

    private List<Long> authorities;

    private Long creator;
}