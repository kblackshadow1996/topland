package cn.topland.entity;

import cn.topland.util.UUIDGenerator;
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
     * 异常属性
     */
    @Enumerated(value = EnumType.STRING)
    private Attribute attribute;

    /**
     * 订单
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
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
    private LocalDate createDate;

    /**
     * 归属部门
     */
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    /**
     * 责任人
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "exception_owner",
            joinColumns = {@JoinColumn(name = "exception_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> owners;

    /**
     * 抄送人
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
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
    private String attachments;

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
     * 是否重大异常
     */
    private Boolean critical;

    /**
     * 是否已处理
     */
    private Boolean resolved;

    /**
     * 解决日期
     */
    private LocalDate closeDate;

    /**
     * 实际损失金额
     */
    private BigDecimal actualLoss;

    /**
     * 实际损失情况
     */
    private String actualLossCondition;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 最优解决方案
     */
    private String optimalSolution;

    /**
     * 是否实施最优解
     */
    private Boolean optimal;

    /**
     * 唯一标识符
     */
    private String uuid = UUIDGenerator.generate();

    public enum Action {

        CREATE,
        UPDATE,
        CREATE_SOLUTION,
        UPDATE_SOLUTION
    }

    public enum Attribute {

        INTERNAL, // 内部异常
        ORDER // 订单异常
    }

    public enum DepartmentSource {

        SUPPLIER, // 供应商
        INTERNAL // 内部
    }
}