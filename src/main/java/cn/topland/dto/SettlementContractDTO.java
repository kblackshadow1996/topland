package cn.topland.dto;

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
public class SettlementContractDTO implements Serializable {

    private Long id;

    private String identity;

    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    private BigDecimal receivable;

    private Long order;

    private String remark;

    private SettlementContract.Status status;

    private List<Long> attachments;

    private Long contract;

    private Long creator;
}