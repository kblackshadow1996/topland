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

    private Long id;

    private String name;

    @JsonProperty(value = "directus_role")
    private String directusRole;

    private String remark;

    private List<Long> authorities;

    private Long creator;

    private Long editor;

    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @JsonProperty(value = "last_update_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateTime;
}