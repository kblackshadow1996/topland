package cn.topland.entity.directus;

import cn.topland.entity.SettlementContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class SettlementContractDO extends DirectusRecordEntity {

    private String identity;

    @JsonProperty(value = "contract_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractDate;

    private BigDecimal receivable;

    private Long order;

    private String remark;

    private List<Long> attachments;

    private String status;

    private Long contract;

    public static SettlementContractDO from(SettlementContract contract) {

        SettlementContractDO contractDO = new SettlementContractDO();
        contractDO.setIdentity(contract.getIdentity());
        contractDO.setContractDate(contract.getContractDate());
        contractDO.setReceivable(contract.getReceivable());
        contractDO.setOrder(contract.getOrder() == null ? null : contract.getOrder().getId());
        contractDO.setRemark(contract.getRemark());
        contractDO.setStatus(contract.getStatus().name());
        contractDO.setContract(contract.getContract() == null ? null : contract.getContract().getId());
        contractDO.setCreator(contract.getCreator().getId());
        contractDO.setEditor(contract.getEditor().getId());
        contractDO.setCreateTime(contract.getCreateTime());
        contractDO.setLastUpdateTime(contract.getLastUpdateTime());
        return contractDO;
    }
}