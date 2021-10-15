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

    private Exception.Attribute attribute;

    private Long type;

    @JsonProperty(value = "department_source")
    private Exception.DepartmentSource departmentSource;

    private Long department;

    private List<Long> orders;

    private List<Long> owners;

    private List<Long> copies;

    private Long judge;

    private String complaint;

    private List<AttachmentVO> attachments;

    @JsonProperty(value = "self_check")
    private String selfCheck;

    private String narrative;

    @JsonProperty(value = "estimated_loss")
    private BigDecimal estimatedLoss;

    @JsonProperty(value = "estimated_loss_condition")
    private String estimatedLossCondition;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "create_date")
    private LocalDate createDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "close_date")
    private LocalDate closeDate;

    @JsonProperty(value = "actual_loss")
    private BigDecimal actualLoss;

    @JsonProperty(value = "actual_loss_condition")
    private String actualLossCondition;

    private String solution;

    @JsonProperty(value = "optimal_solution")
    private String optimalSolution;

    private Boolean optimal;

    private Long creator;
}