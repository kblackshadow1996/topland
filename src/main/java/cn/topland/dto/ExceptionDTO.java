package cn.topland.dto;

import cn.topland.entity.Exception;
import cn.topland.entity.directus.Buffer;
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

    private Long id;

    private String attribute;

    private List<Long> orders;

    private Long type;

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

    private Buffer critical;

    private Buffer resolved;

    @JsonProperty(value = "create_date")
    private LocalDate createDate;

    @JsonProperty(value = "close_date")
    private LocalDate closeDate;

    @JsonProperty(value = "actual_loss")
    private BigDecimal actualLoss;

    @JsonProperty(value = "actual_loss_condition")
    private String actualLossCondition;

    private String solution;

    @JsonProperty(value = "optimal_solution")
    private String optimalSolution;

    private Buffer optimal;
}