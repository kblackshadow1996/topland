package cn.topland.entity.directus;

import cn.topland.entity.User;
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

    private boolean active;

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

    public static UserDO from(User user) {

        UserDO userDO = new UserDO();
        userDO.setName(user.getName());
        userDO.setEmployeeId(user.getEmployeeId());
        userDO.setUserId(user.getUserId());
        userDO.setExternalPosition(user.getExternalPosition());
        userDO.setInternalPosition(user.getInternalPosition());
        userDO.setMobile(user.getMobile());
        userDO.setEmail(user.getEmail());
        userDO.setAvatar(user.getAvatar());
        userDO.setLeadDepartments(user.getLeadDepartments());
        userDO.setRemark(user.getRemark());
        userDO.setSource(user.getSource().name());
        userDO.setDirectusUser(user.getDirectusUser() == null ? null : user.getDirectusUser().getId());
        userDO.setDirectusEmail(user.getDirectusEmail());
        userDO.setDirectusPassword(user.getDirectusPassword());
        userDO.setAuth(user.getAuth() == null ? null : user.getAuth().name());
        userDO.setRole(user.getRole() == null ? null : user.getRole().getId());
        userDO.setCreator(user.getCreator().getId());
        userDO.setEditor(user.getEditor().getId());
        userDO.setCreateTime(user.getCreateTime());
        userDO.setLastUpdateTime(user.getLastUpdateTime());
        return userDO;
    }
}