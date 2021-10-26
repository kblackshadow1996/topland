package cn.topland.entity.directus;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class DirectusSimpleIdEntity implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long id;
}