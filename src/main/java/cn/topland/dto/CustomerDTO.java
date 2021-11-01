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

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 销售ID
     */
    private Long seller;

    /**
     * 经营信息
     */
    private String business;

    /**
     * 客户类型
     */
    private String type;

    /**
     * 客户来源
     */
    private String status;

    /**
     * 父公司ID
     */
    private Long parent;

    /**
     * 来源
     */
    private String source;

    /**
     * 发票类型
     */
    @JsonProperty(value = "invoice_type")
    private String invoiceType;

    /**
     * 税号
     */
    private String identity;

    /**
     * 邮寄地址
     */
    @JsonProperty(value = "post_address")
    private String postAddress;

    /**
     * 注册地址
     */
    @JsonProperty(value = "register_address")
    private String registerAddress;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 银行
     */
    private String bank;

    /**
     * 账户
     */
    private String account;

    /**
     * 联系人ID
     */
    private List<Long> contacts;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 修改人ID
     */
    private Long editor;

    /**
     * 创建时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}