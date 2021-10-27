package cn.topland.dao.gateway;

import cn.topland.entity.Contact;
import cn.topland.entity.directus.ContactDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactGateway extends BaseGateway {

    @Value("${directus.items.contact}")
    private String CONTACT_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<ContactDO>> CONTACTS = new TypeReference<>() {
    };

    public List<ContactDO> saveAll(List<Contact> contacts, String accessToken) {

        List<ContactDO> contactDOs = createAll(getCreateContacts(contacts), accessToken);
        contactDOs.addAll(updateAll(getUpdateContacts(contacts), accessToken));
        return contactDOs;
    }

    private List<ContactDO> createAll(List<Contact> contacts, String accessToken) {

        Reply result = directus.post(CONTACT_URI, tokenParam(accessToken), composeContacts(contacts));
        String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
        return JsonUtils.parse(data, CONTACTS);
    }

    private List<ContactDO> updateAll(List<Contact> contacts, String accessToken) {

        List<ContactDO> contactDOs = new ArrayList<>();
        for (Contact contact : contacts) {

            Reply result = directus.patch(CONTACT_URI + "/" + contact.getId(), tokenParam(accessToken), JsonUtils.toJsonNode(ContactDO.from(contact)));
            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            contactDOs.add(JsonUtils.parse(data, ContactDO.class));
        }
        return contactDOs;
    }

    private List<Contact> getCreateContacts(List<Contact> contacts) {

        return contacts.stream().filter(contact -> contact.getId() == null).collect(Collectors.toList());
    }

    private List<Contact> getUpdateContacts(List<Contact> contacts) {

        return contacts.stream().filter(contact -> contact.getId() != null).collect(Collectors.toList());
    }

    private JsonNode composeContacts(List<Contact> contacts) {

        List<ContactDO> contactDOs = contacts.stream().map(ContactDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(contactDOs);
    }
}