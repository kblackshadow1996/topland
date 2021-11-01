package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class SolutionVO implements Serializable {

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