package cn.topland.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 企业微信用户信息
 */
@Setter
@Getter
@NoArgsConstructor
public class WeworkUser extends BaseResponse {

    @JsonProperty("userid")
    private String userId;

    private String name;

    private List<String> department;

    private List<String> order;

    private String position;

    private String mobile;

    private String gender;

    private String email;

    @JsonProperty("is_leader_in_dept")
    private List<String> isLeaderInDept;

    private String avatar;

    @JsonProperty("thumb_avatar")
    private String thumbAvatar;

    private String telephone;

    private String alias;

    private String address;

    @JsonProperty("open_userid")
    private String openUserId;

    @JsonProperty("main_department")
    private String mainDepartment;

    private String status;

    @JsonProperty("qr_code")
    private String qrCode;

    @JsonProperty("external_position")
    private String externalPosition;
}