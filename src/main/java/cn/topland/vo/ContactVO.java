package cn.topland.vo;

import cn.topland.entity.Contact.Gender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ContactVO implements Serializable {

    /**
     * 联系人ID,新增联系人不用传
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Gender gender;

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