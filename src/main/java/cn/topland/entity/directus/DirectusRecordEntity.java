package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DirectusRecordEntity extends DirectusIdEntity {

    private Long creator;

    private Long editor;
}