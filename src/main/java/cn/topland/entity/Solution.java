package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "solution")
public class Solution extends RecordEntity {

    /**
     * 解决日期
     */
    private LocalDate date;

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
}