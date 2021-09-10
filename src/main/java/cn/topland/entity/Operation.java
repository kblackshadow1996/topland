package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 操作记录
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "operation")
public class Operation extends RecordEntity {

    /**
     * 操作
     */
    private String operation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 模块
     */
    private Module module;

    public enum Module {

        CUSTOMER, // 客户
        BRAND // 品牌
    }
}