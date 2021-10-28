package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class RoleVO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限组ID
     */
    private List<Long> authorities;

    /**
     * 创建人ID
     */
    private Long creator;
}