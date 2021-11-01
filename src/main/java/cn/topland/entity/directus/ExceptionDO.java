package cn.topland.entity.directus;

import cn.topland.entity.Exception;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String attachments;

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

    @Setter
    @Getter
    public static class BaseException {

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

        private String attachments;

        @JsonProperty(value = "self_check")
        private String selfCheck;

        private String narrative;

        @JsonProperty(value = "estimated_loss")
        private BigDecimal estimatedLoss;

        @JsonProperty(value = "estimated_loss_condition")
        private String estimatedLossCondition;

        private Long creator;

        private Long editor;

        @JsonProperty(value = "create_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createTime;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private String uuid;
    }

    @Setter
    @Getter
    public static class Solution {

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

        private Long editor;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;
    }

    public static Solution solution(Exception exception) {

        Solution solution = new Solution();
        solution.setCloseDate(exception.getCloseDate());
        solution.setActualLoss(exception.getActualLoss());
        solution.setActualLossCondition(exception.getActualLossCondition());
        solution.setSolution(exception.getSolution());
        solution.setOptimalSolution(exception.getOptimalSolution());
        solution.setOptimal(exception.getOptimal());
        solution.setEditor(exception.getEditor().getId());
        solution.setLastUpdateTime(exception.getLastUpdateTime());
        return solution;
    }

    public static BaseException from(Exception exception) {

        BaseException base = new BaseException();
        base.setAttachments(exception.getAttachments());
        base.setAttribute(exception.getAttribute().name());
        base.setType(exception.getType() == null ? null : exception.getType().getId());
        base.setCreateDate(exception.getCreateDate());
        base.setDepartment(exception.getDepartment() == null ? null : exception.getDepartment().getId());
        base.setJudge(exception.getJudge() == null ? null : exception.getJudge().getId());
        base.setComplaint(exception.getComplaint());
        base.setSelfCheck(exception.getSelfCheck());
        base.setNarrative(exception.getNarrative());
        base.setEstimatedLoss(exception.getEstimatedLoss());
        base.setEstimatedLossCondition(exception.getEstimatedLossCondition());
        base.setCreator(exception.getCreator().getId());
        base.setEditor(exception.getEditor().getId());
        base.setCreateTime(exception.getCreateTime());
        base.setLastUpdateTime(exception.getLastUpdateTime());
        base.setUuid(exception.getUuid());
        return base;
    }
}