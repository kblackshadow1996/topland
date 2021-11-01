package cn.topland.vo;

import cn.topland.entity.Exception;
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
public class ExceptionVO implements Serializable {

    /**
     * 属性
     */
    private Exception.Attribute attribute;

    /**
     * 异常类型ID
     */
    private Long type;

    /**
     * 部门ID
     */
    private Long department;

    /**
     * 订单ID
     */
    private List<Long> orders;

    /**
     * 负责人ID
     */
    private List<Long> owners;

    /**
     * 抄送人ID
     */
    private List<Long> copies;

    /**
     * 判定人ID
     */
    private Long judge;

    /**
     * 客户投诉内容
     */
    private String complaint;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 自检内容
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
     * 异常日期：yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "create_date")
    private LocalDate createDate;

    /**
     * 解决日期：yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "close_date")
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

    /**
     * 创建人ID
     */
    private Long creator;
}