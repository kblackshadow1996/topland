package cn.topland.dto.converter;

import cn.topland.dto.CustomerDTO;
import cn.topland.entity.Contact;
import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter extends BaseConverter<Customer, CustomerDTO> {

    @Override
    public CustomerDTO toDTO(Customer customer) {

        return customer != null
                ? composeCustomerDTO(customer)
                : null;
    }

    private CustomerDTO composeCustomerDTO(Customer customer) {

        // 基本信息
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setSeller(getId(customer.getSeller()));
        dto.setBusiness(customer.getBusiness());
        dto.setType(customer.getType());
        dto.setStatus(customer.getStatus());
        dto.setParent(getId(customer.getParent()));
        dto.setSource(customer.getSource());

        // 发票
        Invoice invoice = customer.getInvoice();
        dto.setInvoiceType(invoice.getInvoiceType());
        dto.setIdentity(invoice.getIdentity());
        dto.setPostAddress(invoice.getPostAddress());
        dto.setRegisterAddress(invoice.getRegisterAddress());
        dto.setMobile(invoice.getMobile());
        dto.setAccount(invoice.getAccount());
        dto.setBank(invoice.getBank());

        // 联系人
        dto.setContacts(listContactIds(customer.getContacts()));

        // 创建信息
        dto.setCreator(getId(customer.getCreator()));
        dto.setEditor(getId(customer.getEditor()));
        dto.setCreateTime(customer.getCreateTime());
        dto.setLastUpdateTime(customer.getLastUpdateTime());
        return dto;
    }

    private List<Long> listContactIds(List<Contact> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().map(this::getId).collect(Collectors.toList());
    }
}