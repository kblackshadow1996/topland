package cn.topland.vo;

import cn.topland.entity.SettlementContract;
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

    private String identity;

    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    private BigDecimal receivable;

    private Long order;

    private String remark;

    private List<AttachmentVO> attachments;

    private SettlementContract.Status status;

    private Long contract;
}