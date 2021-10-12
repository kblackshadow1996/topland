package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static cn.topland.entity.Customer.Source;
import static cn.topland.entity.Customer.Type;
import static cn.topland.entity.Invoice.InvoiceType;

@Setter
@Getter
public class CustomerVO implements Serializable {

    private Long id;

    private String name;

    private Long seller;

    private String business;

    private Type type;

    private Long parent;

    private Source source;

    @JsonProperty("invoice_type")
    private InvoiceType invoiceType;

    private String identity;

    @JsonProperty("post_address")
    private String postAddress;

    @JsonProperty("register_address")
    private String registerAddress;

    private String mobile;

    private String bank;

    private String account;

    private List<ContactVO> contacts;
}