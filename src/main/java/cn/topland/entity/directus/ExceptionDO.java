package cn.topland.entity.directus;

import cn.topland.entity.Exception;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ExceptionDO extends DirectusRecordEntity {

    private String attribute;

    private List<Long> orders;

    private Long type;

    @JsonProperty(value = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    private Long department;

    private List<Long> owners;

    private List<Long> copies;

    private Long judge;

    private String complaint;

    private List<Long> attachments;

    @JsonProperty(value = "self_check")
    private String selfCheck;

    private String narrative;

    @JsonProperty(value = "estimated_loss")
    private BigDecimal estimatedLoss;

    @JsonProperty(value = "estimated_loss_condition")
    private String estimatedLossCondition;

    private Boolean critical;

    private Boolean resolved;

    @JsonProperty(value = "close_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @JsonProperty(value = "actual_loss")
    private BigDecimal actualLoss;

    @JsonProperty(value = "actual_loss_condition")
    private String actualLossCondition;

    private String solution;

    @JsonProperty(value = "optimal_solution")
    private String optimalSolution;

    private Boolean optimal;

    private String uuid;

    public static ExceptionDO from(Exception exception) {

        ExceptionDO exceptionDO = new ExceptionDO();
        exceptionDO.setAttribute(exception.getAttribute().name());
        exceptionDO.setType(exception.getType() == null ? null : exception.getType().getId());
        exceptionDO.setCreateDate(exception.getCreateDate());
        exceptionDO.setDepartment(exception.getDepartment() == null ? null : exception.getDepartment().getId());
        exceptionDO.setJudge(exception.getJudge() == null ? null : exception.getJudge().getId());
        exceptionDO.setComplaint(exception.getComplaint());
        exceptionDO.setSelfCheck(exception.getSelfCheck());
        exceptionDO.setNarrative(exception.getNarrative());
        exceptionDO.setEstimatedLoss(exception.getEstimatedLoss());
        exceptionDO.setEstimatedLossCondition(exception.getEstimatedLossCondition());
        exceptionDO.setCloseDate(exception.getCloseDate());
        exceptionDO.setActualLoss(exception.getActualLoss());
        exceptionDO.setActualLossCondition(exception.getActualLossCondition());
        exceptionDO.setSolution(exception.getSolution());
        exceptionDO.setOptimalSolution(exception.getOptimalSolution());
        exceptionDO.setCreator(exception.getCreator().getId());
        exceptionDO.setEditor(exception.getEditor().getId());
        exceptionDO.setCreateTime(exception.getCreateTime());
        exceptionDO.setLastUpdateTime(exception.getLastUpdateTime());
        exceptionDO.setUuid(exception.getUuid());
        return exceptionDO;
    }
}