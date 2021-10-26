package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrandDO extends DirectusRecordEntity {

    private String name;

    private Long seller;

    private Long producer;

    private String business;

    private List<Long> contacts;

    private Long customer;
}