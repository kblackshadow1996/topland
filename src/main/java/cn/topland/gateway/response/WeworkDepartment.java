package cn.topland.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeworkDepartment extends BaseResponse {

    private String id;

    private String name;

    @JsonProperty("name_en")
    private String enName;

    @JsonProperty("parentid")
    private String parentId;

    private String order;
}