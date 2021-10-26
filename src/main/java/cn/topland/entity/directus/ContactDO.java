package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactDO extends DirectusIdEntity {

    private String name;

    private String gender;

    private String mobile;

    private String position;

    private String department;

    private String address;

    private String remark;

    private Long customer;

    private Long brand;
}