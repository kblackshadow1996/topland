package cn.topland.service;

import cn.topland.dao.ContactRepository;
import cn.topland.entity.Contact;
import cn.topland.entity.IdEntity;
import cn.topland.vo.ContactVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    private Contact updateContact(Contact contact, ContactVO contactVO) {

        return composeContact(contact, contactVO);
    }

    private Contact createContact(ContactVO contactVO) {

        return composeContact(new Contact(), contactVO);
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