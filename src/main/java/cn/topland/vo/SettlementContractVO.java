package cn.topland.vo;

import cn.topland.entity.SettlementContract;
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
public class SettlementContractVO implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    private BigDecimal receivable;

    private Long order;

    private String remark;

    private List<AttachmentVO> attachments;

    private Long contract;

    private Long creator;

    private SettlementContract.Action action;

    @JsonProperty("review_comments")
    private String reviewComments;
}