package cn.topland.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Users extends BaseResponse {

    @JsonProperty("userlist")
    private List<WeworkUser> weworkUsers;
}