package cn.topland.entity.directus;

import cn.topland.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private Boolean active;

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

    @Setter
    @Getter
    public static class BaseUser {

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

        private Boolean active;

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

        @JsonProperty(value = "create_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createTime;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long creator;

        private Long editor;
    }

    @Setter
    @Getter
    public static class Auth {

        private String auth;

        private Long role;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long editor;
    }

    public static BaseUser from(User user) {

        BaseUser base = new BaseUser();
        base.setName(user.getName());
        base.setEmployeeId(user.getEmployeeId());
        base.setUserId(user.getUserId());
        base.setExternalPosition(user.getExternalPosition());
        base.setInternalPosition(user.getInternalPosition());
        base.setMobile(user.getMobile());
        base.setEmail(user.getEmail());
        base.setAvatar(user.getAvatar());
        base.setLeadDepartments(user.getLeadDepartments());
        base.setRemark(user.getRemark());
        base.setSource(user.getSource().name());
        base.setDirectusUser(user.getDirectusUser() == null ? null : user.getDirectusUser().getId());
        base.setDirectusEmail(user.getDirectusEmail());
        base.setDirectusPassword(user.getDirectusPassword());
        base.setAccessToken(user.getAccessToken());
        base.setRefreshToken(user.getRefreshToken());
        base.setCreator(user.getCreator().getId());
        base.setEditor(user.getEditor().getId());
        base.setCreateTime(user.getCreateTime());
        base.setLastUpdateTime(user.getLastUpdateTime());
        return base;
    }

    public static Auth auth(User user) {

        Auth auth = new Auth();
        auth.setAuth(user.getAuth() == null ? null : user.getAuth().name());
        auth.setRole(user.getRole() == null ? null : user.getRole().getId());
        auth.setEditor(user.getId());
        auth.setLastUpdateTime(user.getLastUpdateTime());
        return auth;
    }
}