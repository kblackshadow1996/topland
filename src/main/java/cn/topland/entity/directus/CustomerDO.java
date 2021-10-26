package cn.topland.entity.directus;

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
}