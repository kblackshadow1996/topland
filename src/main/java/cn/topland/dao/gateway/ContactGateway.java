package cn.topland.dao.gateway;

import cn.topland.entity.Contact;
import cn.topland.entity.directus.ContactDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public List<ContactDO> saveAll(List<Contact> contacts, String accessToken) throws InternalException {

        List<ContactDO> contactDOs = createAll(getCreateContacts(contacts), accessToken);
        contactDOs.addAll(updateAll(getUpdateContacts(contacts), accessToken));
        return contactDOs;
    }

    private List<ContactDO> createAll(List<Contact> contacts, String accessToken) throws InternalException {

        Reply result = directus.post(CONTACT_URI, tokenParam(accessToken), composeContacts(contacts));
        if (result.isSuccessful()) {

            String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
            return JsonUtils.parse(data, CONTACTS);
        }
        throw new InternalException("创建客户联系人失败");
    }

    private List<ContactDO> updateAll(List<Contact> contacts, String accessToken) throws InternalException {

        List<ContactDO> contactDOs = new ArrayList<>();
        for (Contact contact : contacts) {

            Reply result = directus.patch(CONTACT_URI + "/" + contact.getId(), tokenParam(accessToken), composeContact(contact));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                contactDOs.add(JsonUtils.parse(data, ContactDO.class));
            } else {

                throw new InternalException("更新客户联系人失败");
            }
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

        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        contacts.forEach(contact -> {

            array.add(composeContact(contact));
        });
        return array;
    }

    private JsonNode composeContact(Contact contact) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", contact.getName());
        node.put("gender", contact.getGender().name());
        node.put("mobile", contact.getMobile());
        node.put("position", contact.getPosition());
        node.put("department", contact.getDepartment());
        node.put("address", contact.getAddress());
        node.put("remark", contact.getRemark());
        node.put("customer", contact.getCustomer());
        node.put("brand", contact.getBrand());
        return node;
    }
}