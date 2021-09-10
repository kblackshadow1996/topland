package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 联系人
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact extends IdEntity {

    /**
     * 姓名
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

    public enum Gender {

        MALE, // 男
        FEMALE, // 女
        UNKNOWN; // 未知
    }
}