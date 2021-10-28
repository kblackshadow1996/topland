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
     * 类型
     */
    private Type type;

    /**
     * 父公司ID
     */
    private Long parent;

    /**
     * 来源
     */
    private Source source;

    /**
     * 发票类型
     */
    @JsonProperty("invoice_type")
    private InvoiceType invoiceType;

    /**
     * 税号
     */
    private String identity;

    /**
     * 邮寄地址
     */
    @JsonProperty("post_address")
    private String postAddress;

    /**
     * 注册地址
     */
    @JsonProperty("register_address")
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

    private List<ContactVO> contacts;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 流失原因
     */
    @JsonProperty("lost_reason")
    private String lostReason;
}