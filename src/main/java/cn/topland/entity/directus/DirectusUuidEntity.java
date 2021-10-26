package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class DirectusUuidEntity implements Serializable {

    protected String id;
}