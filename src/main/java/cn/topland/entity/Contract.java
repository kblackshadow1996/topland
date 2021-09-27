package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 合同
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "contract")
public class Contract extends RecordEntity {

    /**
     * 编号
     */
    @Column(unique = true)
    private String identity;

    /**
     * 类型
     */
    private Type type;

    /**
     * 客户
     */
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    /**
     * 品牌
     */
    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    /**
     * 签约日期
     */
    private LocalDate contractDate;

    /**
     * 收到纸质合同日期
     */
    private LocalDate paperDate;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 保证金
     */
    private BigDecimal margin;

    /**
     * 保底拍摄费
     */
    private BigDecimal guarantee;

    /**
     * 应收金额
     */
    private BigDecimal receivable;

    /**
     * 销售
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联订单
     */
    @OneToOne
    @JoinColumn(name = "`order`")
    private Order order;

    /**
     * 结算合同
     */
    @OneToMany
    @JoinColumn(name = "contract")
    private List<SettlementContract> settlements;

    /**
     * 附件directus_files的id，以分号隔开
     */
    @OneToMany
    @JoinColumn(name = "contract")
    private List<Attachment> attachments;

    /**
     * 收款记录
     */
    @OneToMany
    @JoinColumn(name = "order_contract")
    private List<Receive> receives;

    /**
     * 审核状态
     */
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany
    @JoinColumn(name = "contract")
    private List<Operation> operations;

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

    public enum Type {

        YEAR, // 年框
        ORDER // 订单
    }
}