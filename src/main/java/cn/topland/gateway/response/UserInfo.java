package cn.topland.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BaseResponse {

    @JsonProperty("UserId")
    private String userId;

    @JsonProperty("OpenId")
    private String openId;
}