package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 结算合同
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "settlement_contract")
public class SettlementContract extends RecordEntity {

    /**
     * 编号
     */
    @Column(unique = true)
    private String identity;

    /**
     * 签约日期
     */
    private LocalDate contractDate;

    /**
     * 应收金额
     */
    private BigDecimal receivable;

    /**
     * 关联订单
     */
    @OneToOne
    @JoinColumn(name = "`order`")
    private Order order;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收款记录
     */
    @OneToMany
    @JoinColumn(name = "settlement_contract")
    private List<Receive> receives;

    /**
     * 附件
     */
    private String attachments;

    /**
     * 状态
     */
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "contract")
    private Contract contract;

    public enum Status {

        REVIEWING,
        REJECTED,
        APPROVED
    }

    public enum Action {

        SUBMIT,
        REJECT,
        APPROVE
    }
}