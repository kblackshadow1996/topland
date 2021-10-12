package cn.topland.dto.converter;

import cn.topland.dto.ContactDTO;
import cn.topland.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactConverter {

    public List<ContactDTO> toContactDTOs(List<Contact> contacts) {

        return contacts.stream().map(this::toContactDTO).collect(Collectors.toList());
    }

    private ContactDTO toContactDTO(Contact contact) {

        return contact != null
                ? composeContactDTO(contact)
                : null;
    }

    private ContactDTO composeContactDTO(Contact contact) {

        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setMobile(contact.getMobile());
        dto.setAddress(contact.getAddress());
        dto.setPosition(contact.getPosition());
        dto.setDepartment(contact.getDepartment());
        dto.setRemark(contact.getRemark());
        return dto;
    }
}