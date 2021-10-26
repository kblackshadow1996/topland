package cn.topland.entity.directus;

import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerDO extends DirectusRecordEntity {

    private String name;

    private Long seller;

    private String business;

    private String type;

    private Long parent;

    private String source;

    private String status;

    @JsonProperty(value = "invoice_type")
    private String invoiceType;

    private String identity;

    @JsonProperty(value = "post_address")
    private String postAddress;

    @JsonProperty(value = "register_address")
    private String registerAddress;

    private String mobile;

    private String bank;

    private String account;

    private List<Long> contacts;

    public static CustomerDO from(Customer customer) {

        CustomerDO customerDO = new CustomerDO();
        customerDO.setName(customer.getName());
        customerDO.setSeller(customer.getSeller() == null ? null : customer.getSeller().getId());
        customerDO.setBusiness(customer.getBusiness());
        customerDO.setType(customer.getType().name());
        customerDO.setParent(customer.getParent() == null ? null : customer.getParent().getId());
        customerDO.setSource(customer.getSource().name());
        customerDO.setStatus(customer.getStatus().name());
        Invoice invoice = customer.getInvoice();
        customerDO.setInvoiceType(invoice.getInvoiceType().name());
        customerDO.setIdentity(invoice.getIdentity());
        customerDO.setPostAddress(invoice.getPostAddress());
        customerDO.setRegisterAddress(invoice.getRegisterAddress());
        customerDO.setMobile(invoice.getMobile());
        customerDO.setBank(invoice.getBank());
        customerDO.setAccount(invoice.getAccount());
        customerDO.setCreator(customer.getCreator().getId());
        customerDO.setEditor(customer.getEditor().getId());
        customerDO.setCreateTime(customer.getCreateTime());
        customerDO.setLastUpdateTime(customer.getLastUpdateTime());
        return customerDO;
    }
}