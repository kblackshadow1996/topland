package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PackageDO extends DirectusRecordEntity {

    private String name;

    private String remark;

    private List<Long> services;
}