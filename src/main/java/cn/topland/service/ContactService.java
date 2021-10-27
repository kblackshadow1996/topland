package cn.topland.service;

import cn.topland.dao.ContactRepository;
import cn.topland.dao.gateway.ContactGateway;
import cn.topland.entity.Contact;
import cn.topland.entity.IdEntity;
import cn.topland.entity.directus.ContactDO;
import cn.topland.vo.ContactVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户、品牌等联系人操作服务
 */
@Component
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactGateway contactGateway;

    public List<ContactDO> createCustomerContacts(List<ContactVO> contactVOs, Long customer, String token) {

        List<Contact> contacts = contactVOs.stream().map(contactVO -> createCustomerContact(contactVO, customer)).collect(Collectors.toList());
        return contactGateway.saveAll(contacts, token);
    }

    public List<ContactDO> updateCustomerContacts(List<Contact> contacts, List<ContactVO> contactVOs, Long customer, String token) {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        List<Contact> customerContacts = new ArrayList<>();
        List<Contact> updates = new ArrayList<>();
        for (ContactVO contactVO : contactVOs) {

            if (contactMap.containsKey(contactVO.getId())) {

                Contact contact = contactMap.get(contactVO.getId());
                updates.add(contact);
                customerContacts.add(updateCustomerContact(contact, contactVO, customer));
            } else {

                customerContacts.add(createCustomerContact(contactVO, customer));
            }
        }
        List<Contact> deletes = (List<Contact>) CollectionUtils.removeAll(contacts, updates);
        deletes.forEach(delete -> {
            // 解除关联
            delete.setCustomer(null);
        });
        customerContacts.addAll(deletes);
        return contactGateway.saveAll(customerContacts, token);
    }

    public List<ContactDO> createBrandContacts(List<ContactVO> contactVOs, Long brand, String token) {

        List<Contact> contacts = contactVOs.stream().map(contactVO -> createBrandContact(contactVO, brand)).collect(Collectors.toList());
        return contactGateway.saveAll(contacts, token);
    }

    public List<ContactDO> updateBrandContacts(List<Contact> contacts, List<ContactVO> contactVOs, Long brand, String token) {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        List<Contact> brandContacts = new ArrayList<>();
        List<Contact> updates = new ArrayList<>();
        for (ContactVO contactVO : contactVOs) {

            if (contactMap.containsKey(contactVO.getId())) {

                Contact contact = contactMap.get(contactVO.getId());
                updates.add(contact);
                brandContacts.add(updateBrandContact(contact, contactVO, brand));
            } else {

                brandContacts.add(createBrandContact(contactVO, brand));
            }
        }
        List<Contact> deletes = (List<Contact>) CollectionUtils.removeAll(contacts, updates);
        deletes.forEach(delete -> {
            // 解除关联
            delete.setBrand(null);
        });
        brandContacts.addAll(deletes);
        return contactGateway.saveAll(brandContacts, token);
    }

    private Contact updateBrandContact(Contact contact, ContactVO contactVO, Long brand) {

        Contact con = composeContact(contact, contactVO);
        con.setBrand(brand);
        return con;
    }

    private Contact updateCustomerContact(Contact contact, ContactVO contactVO, Long customer) {

        Contact con = composeContact(contact, contactVO);
        con.setCustomer(customer);
        return con;
    }

    private Contact createCustomerContact(ContactVO contactVO, Long customer) {

        Contact contact = composeContact(new Contact(), contactVO);
        contact.setCustomer(customer);
        return contact;
    }

    private Contact createBrandContact(ContactVO contactVO, Long brand) {

        Contact contact = composeContact(new Contact(), contactVO);
        contact.setBrand(brand);
        return contact;
    }

    private Contact composeContact(Contact contact, ContactVO contactVO) {

        contact.setName(contactVO.getName());
        contact.setGender(contactVO.getGender());
        contact.setMobile(contactVO.getMobile());
        contact.setAddress(contactVO.getAddress());
        contact.setDepartment(contactVO.getDepartment());
        contact.setPosition(contactVO.getPosition());
        contact.setRemark(contactVO.getRemark());
        return contact;
    }
}