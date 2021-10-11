package cn.topland.vo;

import cn.topland.entity.Contact;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

public class ContactVO implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    @Enumerated(value = EnumType.STRING)
    private Contact.Gender gender;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;
}