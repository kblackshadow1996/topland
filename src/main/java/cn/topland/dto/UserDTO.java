package cn.topland.dto;

import cn.topland.entity.directus.Buffer;
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

    private Long id;

    @JsonProperty(value = "user_id")
    private String userId;

    private String name;

    @JsonProperty(value = "external_position")
    private String externalPosition;

    @JsonProperty(value = "internal_position")
    private String internalPosition;

    private List<Long> departments;

    @JsonProperty(value = "lead_departments")
    private String leadDepartments;

    private String source;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    private String mobile;

    private String avatar;

    private Buffer active;

    private String remark;

    private Long role;

    private String auth;

    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    private Long creator;

    @JsonProperty(value = "directus_user")
    private String directusUser;

    @JsonProperty(value = "directus_email")
    private String directusEmail;

    @JsonProperty(value = "directus_password")
    private String directusPassword;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}