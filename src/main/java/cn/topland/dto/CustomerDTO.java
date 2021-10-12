package cn.topland.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static cn.topland.entity.Customer.Source;
import static cn.topland.entity.Customer.Type;
import static cn.topland.entity.Invoice.InvoiceType;

@Setter
@Getter
public class CustomerDTO implements Serializable {

    private Long id;

    private String name;

    private Long seller;

    private String business;

    private Type type;

    private CustomerDTO parent;

    private Source source;

    private InvoiceType invoiceType;

    private String identity;

    private String postAddress;

    private String registerAddress;

    private String mobile;

    private String bank;

    private String account;

    private List<ContactDTO> contacts;

    private Long creator;

    private Long editor;

    private LocalDateTime createTime;

    private LocalDateTime lastUpdateTime;
}