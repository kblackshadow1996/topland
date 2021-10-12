package cn.topland.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static cn.topland.entity.Contact.Gender;

@Setter
@Getter
public class ContactDTO implements Serializable {

    private Long id;

    private String name;

    private Gender gender;

    private String mobile;

    private String position;

    private String department;

    private String address;

    private String remark;
}