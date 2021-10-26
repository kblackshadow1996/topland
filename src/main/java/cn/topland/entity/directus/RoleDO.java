package cn.topland.entity.directus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleDO extends DirectusRecordEntity {

    private String name;

    @JsonProperty(value = "directus_role")
    private String directusRole;

    private List<Long> authorities;

    private String remark;
}