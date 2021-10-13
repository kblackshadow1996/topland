package cn.topland.dto.converter;

import cn.topland.dto.ContactDTO;
import cn.topland.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactConverter extends BaseConverter<Contact, ContactDTO> {

    @Override
    public List<ContactDTO> toDTOs(List<Contact> contacts) {

        return contacts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ContactDTO toDTO(Contact contact) {

        return contact != null
                ? composeContactDTO(contact)
                : null;
    }

    private ContactDTO composeContactDTO(Contact contact) {

        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setGender(contact.getGender());
        dto.setMobile(contact.getMobile());
        dto.setAddress(contact.getAddress());
        dto.setPosition(contact.getPosition());
        dto.setDepartment(contact.getDepartment());
        dto.setRemark(contact.getRemark());
        return dto;
    }
}