package cn.topland.entity.directus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDO extends DirectusRecordEntity {

    private String name;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "external_position")
    private String externalPosition;

    @JsonProperty(value = "internal_position")
    private String internalPosition;

    private List<Long> departments;

    private String mobile;

    private String email;

    private String avatar;

    @JsonProperty(value = "lead_departments")
    private String leadDepartments;

    private Buffer active;

    private String remark;

    private String source;

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

    private String auth;

    private Long role;
}