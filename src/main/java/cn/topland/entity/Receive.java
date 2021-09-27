package cn.topland.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 收款记录
 */
@Setter
@Getter
@Entity
@Table(name = "receive")
public class Receive extends RecordEntity {

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 收款时间
     */
    private LocalDate receiptDate;
}