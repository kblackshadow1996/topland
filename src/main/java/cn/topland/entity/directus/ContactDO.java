package cn.topland.entity.directus;

import cn.topland.entity.Contact;
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

    public static ContactDO from(Contact contact) {

        ContactDO contactDO = new ContactDO();
        contactDO.setName(contact.getName());
        contactDO.setGender(contact.getGender().name());
        contactDO.setMobile(contact.getMobile());
        contactDO.setPosition(contact.getPosition());
        contactDO.setDepartment(contact.getDepartment());
        contactDO.setAddress(contact.getAddress());
        contactDO.setRemark(contact.getRemark());
        contactDO.setBrand(contact.getBrand());
        contactDO.setCustomer(contact.getCustomer());
        contactDO.setCreateTime(contact.getCreateTime());
        contactDO.setLastUpdateTime(contact.getLastUpdateTime());
        return contactDO;
    }
}