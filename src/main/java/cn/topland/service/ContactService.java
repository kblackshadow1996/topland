package cn.topland.service;

import cn.topland.dao.ContactRepository;
import cn.topland.dao.gateway.ContactGateway;
import cn.topland.entity.Contact;
import cn.topland.entity.IdEntity;
import cn.topland.entity.directus.ContactDO;
import cn.topland.util.exception.InternalException;
import cn.topland.vo.ContactVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<ContactDO> createCustomerContacts(List<ContactVO> contactVOs, Long customer, String token) throws InternalException {

        List<Contact> contacts = contactVOs.stream().map(contactVO -> createCustomerContact(contactVO, customer)).collect(Collectors.toList());
        return contactGateway.saveAll(contacts, token);
    }

    @Transactional
    public List<ContactDO> updateCustomerContacts(List<Contact> contacts, List<ContactVO> contactVOs, Long customer, String token) throws InternalException {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        List<Contact> customerContacts = new ArrayList<>();
        List<Contact> updates = new ArrayList<>();
        for (ContactVO contactVO : contactVOs) {

            if (contactMap.containsKey(contactVO.getId())) {

                Contact contact = contactMap.get(contactVO.getId());
                updates.add(contact);
                customerContacts.add(updateContact(contact, contactVO, customer));
            } else {

                customerContacts.add(createContact(contactVO, customer));
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

    @Transactional
    public List<Contact> createContacts(List<ContactVO> contactVOs) {

        List<Contact> contacts = contactVOs.stream().map(this::createContact).collect(Collectors.toList());
        return contactRepository.saveAllAndFlush(contacts);
    }

    @Transactional
    public List<Contact> updateContacts(List<Contact> contacts, List<ContactVO> contactVOs) {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        return contactVOs.stream().map(contactVO -> {

            return contactMap.containsKey(contactVO.getId())
                    ? updateContact(contactMap.get(contactVO.getId()), contactVO)
                    : createContact(contactVO);
        }).collect(Collectors.toList());
    }

    private Contact updateContact(Contact contact, ContactVO contactVO, Long customer) {

        return composeContact(contact, contactVO, customer);
    }

    private Contact createCustomerContact(ContactVO contactVO, Long customer) {

        Contact contact = composeContact(new Contact(), contactVO);
        contact.setCustomer(customer);
        return contact;
    }

    private Contact updateContact(Contact contact, ContactVO contactVO) {

        return composeContact(contact, contactVO);
    }

    private Contact createContact(ContactVO contactVO, Long customer) {

        return composeContact(new Contact(), contactVO, customer);
    }

    private Contact createContact(ContactVO contactVO) {

        return composeContact(new Contact(), contactVO);
    }

    private Contact composeContact(Contact contact, ContactVO contactVO, Long customer) {

        contact.setCustomer(customer);
        contact.setName(contactVO.getName());
        contact.setGender(contactVO.getGender());
        contact.setMobile(contactVO.getMobile());
        contact.setAddress(contactVO.getAddress());
        contact.setDepartment(contactVO.getDepartment());
        contact.setPosition(contactVO.getPosition());
        contact.setRemark(contactVO.getRemark());
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