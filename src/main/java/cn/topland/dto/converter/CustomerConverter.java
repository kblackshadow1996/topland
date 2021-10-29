package cn.topland.dto.converter;

import cn.topland.dto.CustomerDTO;
import cn.topland.entity.Customer;
import cn.topland.entity.directus.CustomerDO;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter extends BaseConverter<CustomerDO, CustomerDTO> {

    @Override
    public CustomerDTO toDTO(CustomerDO customer) {

        return customer != null
                ? composeCustomerDTO(customer)
                : null;
    }

    private CustomerDTO composeCustomerDTO(CustomerDO customer) {

        // 基本信息
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setSeller(customer.getSeller());
        dto.setBusiness(customer.getBusiness());
        dto.setType(customer.getType());
        dto.setStatus(customer.getStatus());
        dto.setParent(customer.getParent());
        dto.setSource(customer.getSource());

        // 发票
        dto.setInvoiceType(customer.getInvoiceType());
        dto.setIdentity(customer.getIdentity());
        dto.setPostAddress(customer.getPostAddress());
        dto.setRegisterAddress(customer.getRegisterAddress());
        dto.setMobile(customer.getMobile());
        dto.setAccount(customer.getAccount());
        dto.setBank(customer.getBank());

        // 联系人
        dto.setContacts(customer.getContacts());

        // 创建信息
        dto.setCreator(customer.getCreator());
        dto.setEditor(customer.getCreator());
        dto.setCreateTime(customer.getCreateTime());
        dto.setLastUpdateTime(customer.getLastUpdateTime());
        return dto;
    }
}