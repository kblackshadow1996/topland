package cn.topland.dto.converter;

import cn.topland.dto.CustomerDTO;
import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter extends BaseConverter<Customer, CustomerDTO> {

    @Autowired
    private ContactConverter contactConverter;

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
        dto.setSeller(getUserName(customer.getSeller()));
        dto.setBusiness(customer.getBusiness());
        dto.setType(customer.getType());
        dto.setParent(toDTO(customer.getParent()));
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
        dto.setContacts(contactConverter.toDTOs(customer.getContacts()));

        // 创建信息
        dto.setCreator(customer.getCreator().getName());
        dto.setEditor(customer.getEditor().getName());
        dto.setCreateTime(customer.getCreateTime());
        dto.setLastUpdateTime(customer.getLastUpdateTime());
        return dto;
    }
}