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
public class RoleDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * directus角色
     */
    @JsonProperty(value = "directus_role")
    private String directusRole;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色-权限中间表ID
     */
    private List<Long> authorities;

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
}