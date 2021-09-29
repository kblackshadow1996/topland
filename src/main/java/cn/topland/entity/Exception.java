package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 异常
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exception")
public class Exception extends RecordEntity {

    /**
     * 异常分类
     */
    @Enumerated(EnumType.STRING)
    private Classification classification;

    /**
     * 订单
     */
    @ManyToMany
    @JoinTable(name = "exception_order",
            joinColumns = {@JoinColumn(name = "exception_id")}, inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private List<Order> orders;

    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "type")
    private ExceptionType type;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 归属部门
     */
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    /**
     * 责任人
     */
    @ManyToMany
    @JoinTable(name = "exception_owner",
            joinColumns = {@JoinColumn(name = "exception_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> owners;

    /**
     * 抄送人
     */
    @ManyToMany
    @JoinTable(name = "exception_copy",
            joinColumns = {@JoinColumn(name = "exception_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> copies;

    /**
     * 判定人
     */
    @ManyToOne
    @JoinColumn(name = "judge")
    private User judge;

    /**
     * 投诉概述
     */
    private String complaint;

    /**
     * 投诉附件
     */
    @OneToMany
    @JoinColumn(name = "exception")
    private List<Attachment> attachments;

    /**
     * 自检内容
     */
    private String selfCheck;

    /**
     * 叙述过程
     */
    private String narrative;

    /**
     * 预估损失金额
     */
    private BigDecimal estimatedLoss;

    /**
     * 预估损失情况
     */
    private String estimatedLossCondition;

    /**
     * 处理
     */
    @OneToOne
    @JoinColumn(name = "solution")
    private Solution solution;

    /**
     * 等级
     */
    @Enumerated(EnumType.STRING)
    private Level level;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 操作记录
     */
    @OneToMany
    @JoinColumn(name = "exception")
    private List<Operation> operations;

    public enum Level {

        NORMAL,
        CRITICAL // 重大
    }

    public enum Status {

        RESOLVED, // 已解决
        UNRESOLVED // 待处理
    }

    public enum Classification {

        INTERNAL, // 内部
        ORDER // 订单
    }

    public enum Action {

        CREATE,
        UPDATE,
        CREATE_SOLUTION,
        UPDATE_SOLUTION
    }
}