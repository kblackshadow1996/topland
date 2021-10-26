package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class DirectusSimpleIdEntity implements Serializable {

    protected Long id;
}