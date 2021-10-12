package cn.topland.dto.converter;

import cn.topland.dto.CustomerDTO;
import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ContactConverter contactConverter;

    public CustomerDTO toCustomerDTO(Customer customer) {

        return customer != null
                ? composeCustomerDTO(customer)
                : null;
    }

    private CustomerDTO composeCustomerDTO(Customer customer) {

        // 基本信息
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setSeller(userConverter.toUserDTO(customer.getSeller()));
        dto.setBusiness(customer.getBusiness());
        dto.setType(customer.getType());
        dto.setParent(toCustomerDTO(customer.getParent()));
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
        dto.setContacts(contactConverter.toContactDTOs(customer.getContacts()));

        // 创建信息
        dto.setCreator(userConverter.toUserDTO(customer.getCreator()));
        dto.setEditor(userConverter.toUserDTO(customer.getEditor()));
        dto.setCreateTime(customer.getCreateTime());
        dto.setLastUpdateTime(customer.getLastUpdateTime());
        return dto;
    }
}