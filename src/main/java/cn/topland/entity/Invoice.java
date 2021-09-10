package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 发票
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice extends IdEntity {

    /**
     * 类型
     */
    private Type type;

    /**
     * 纳税人识别号
     */
    private String identify;

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

    public enum Type {

        NORMAL, // 普通
        SPECIAL // 专用
    }
}