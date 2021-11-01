package cn.topland.entity.directus;

import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Setter
    @Getter
    public static class BaseCustomer {

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

        @JsonProperty(value = "create_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createTime;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long creator;

        private Long editor;
    }

    @Setter
    @Getter
    public static class LostAndRetrieve {

        private String status;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long editor;
    }

    public static BaseCustomer from(Customer customer) {

        BaseCustomer base = new BaseCustomer();
        base.setName(customer.getName());
        base.setSeller(customer.getSeller() == null ? null : customer.getSeller().getId());
        base.setBusiness(customer.getBusiness());
        base.setType(customer.getType().name());
        base.setParent(customer.getParent() == null ? null : customer.getParent().getId());
        base.setSource(customer.getSource().name());
        base.setStatus(customer.getStatus().name());
        Invoice invoice = customer.getInvoice();
        base.setInvoiceType(invoice.getInvoiceType().name());
        base.setIdentity(invoice.getIdentity());
        base.setPostAddress(invoice.getPostAddress());
        base.setRegisterAddress(invoice.getRegisterAddress());
        base.setMobile(invoice.getMobile());
        base.setBank(invoice.getBank());
        base.setAccount(invoice.getAccount());
        base.setCreator(customer.getCreator().getId());
        base.setEditor(customer.getEditor().getId());
        base.setCreateTime(customer.getCreateTime());
        base.setLastUpdateTime(customer.getLastUpdateTime());
        return base;
    }

    public static LostAndRetrieve lostAndRetrieve(Customer customer) {

        LostAndRetrieve lostAndRetrieve = new LostAndRetrieve();
        lostAndRetrieve.setStatus(customer.getStatus().name());
        lostAndRetrieve.setEditor(customer.getEditor().getId());
        lostAndRetrieve.setLastUpdateTime(customer.getLastUpdateTime());
        return lostAndRetrieve;
    }
}