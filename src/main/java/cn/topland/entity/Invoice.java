package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 发票
 */
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Invoice {

    /**
     * 类型
     */
    @Enumerated(value = EnumType.STRING)
    private InvoiceType invoiceType;

    /**
     * 纳税人识别号
     */
    private String identity;

    /**
     * 邮寄地址
     */
    private String postAddress;

    /**
     * 注册地址
     */
    private String registerAddress;

    /**
     * 注册电话
     */
    private String mobile;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 打款账号
     */
    private String account;

    public enum InvoiceType {

        NORMAL, // 普通
        SPECIAL // 专用
    }
}