package cn.topland.vo;

import cn.topland.entity.Contact.Gender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ContactVO implements Serializable {

    private Long id;

    private String name;

    private Gender gender;

    private String mobile;

    private String position;

    private String department;

    private String address;

    private String remark;
}