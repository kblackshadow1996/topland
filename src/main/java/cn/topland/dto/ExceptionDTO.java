package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ExceptionDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 属性
     */
    private String attribute;

    /**
     * 订单ID
     */
    private List<Long> orders;

    /**
     * 异常类型ID
     */
    private Long type;

    /**
     * 部门ID
     */
    private Long department;

    /**
     * 异常-负责人中间表ID
     */
    private List<Long> owners;

    /**
     * 异常-抄送人中间表ID
     */
    private List<Long> copies;

    /**
     * 判定人ID
     */
    private Long judge;

    /**
     * 投诉
     */
    private String complaint;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 自检
     */
    @JsonProperty(value = "self_check")
    private String selfCheck;

    /**
     * 三方叙述
     */
    private String narrative;

    /**
     * 预估损失金额
     */
    @JsonProperty(value = "estimated_loss")
    private BigDecimal estimatedLoss;

    /**
     * 预估损失情况
     */
    @JsonProperty(value = "estimated_loss_condition")
    private String estimatedLossCondition;

    /**
     * 是否重大异常
     */
    private Boolean critical;

    /**
     * 是否解决
     */
    private Boolean resolved;

    /**
     * 异常日期：yyyy-MM-dd
     */
    @JsonProperty(value = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    /**
     * 解决日期：yyyy-MM-dd
     */
    @JsonProperty(value = "close_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    /**
     * 实际损失金额
     */
    @JsonProperty(value = "actual_loss")
    private BigDecimal actualLoss;

    /**
     * 实际损失情况
     */
    @JsonProperty(value = "actual_loss_condition")
    private String actualLossCondition;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 最优解决方案
     */
    @JsonProperty(value = "optimal_solution")
    private String optimalSolution;

    /**
     * 是否最优
     */
    private Boolean optimal;
}