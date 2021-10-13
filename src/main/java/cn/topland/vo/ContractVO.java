package cn.topland.vo;

import cn.topland.entity.Contract;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ContractVO implements Serializable {

    private String identity;

    private Contract.Type type;

    private Long customer;

    private Long brand;

    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    @JsonProperty(value = "paper_date")
    private LocalDate paperDate;

    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    private BigDecimal margin;

    private BigDecimal guarantee;

    private BigDecimal receivable;

    private Long seller;

    private String remark;

    private Long order;

    private List<AttachmentVO> attachments;

    private Long creator;

    private Contract.Action action;

    @JsonProperty("review_comments")
    private String reviewComments;
}