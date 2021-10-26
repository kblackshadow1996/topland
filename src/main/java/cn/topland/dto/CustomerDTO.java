package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class CustomerDTO implements Serializable {

    private Long id;

    private String name;

    private Long seller;

    private String business;

    private String type;

    private String status;

    private Long parent;

    private String source;

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

    private Long creator;

    private Long editor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}