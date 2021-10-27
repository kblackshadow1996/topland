package cn.topland.entity.directus;

import cn.topland.entity.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ContractDO extends DirectusRecordEntity {

    private String identity;

    private String type;

    private Long customer;

    private Long brand;

    @JsonProperty(value = "contract_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractDate;

    @JsonProperty(value = "paper_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paperDate;

    @JsonProperty(value = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private BigDecimal margin;

    private BigDecimal guarantee;

    private BigDecimal receivable;

    private Long seller;

    private String remark;

    private Long order;

    private List<Long> attachments;

    private String status;

    public static ContractDO from(Contract contract) {

        ContractDO contractDO = new ContractDO();
        contractDO.setIdentity(contract.getIdentity());
        contractDO.setType(contract.getType().name());
        contractDO.setCustomer(contract.getCustomer() == null ? null : contract.getCustomer().getId());
        contractDO.setBrand(contract.getBrand() == null ? null : contract.getBrand().getId());
        contractDO.setContractDate(contract.getContractDate());
        contractDO.setPaperDate(contract.getPaperDate());
        contractDO.setStartDate(contract.getStartDate());
        contractDO.setEndDate(contract.getEndDate());
        contractDO.setMargin(contract.getMargin());
        contractDO.setGuarantee(contract.getGuarantee());
        contractDO.setReceivable(contract.getReceivable());
        contractDO.setSeller(contract.getSeller() == null ? null : contract.getSeller().getId());
        contractDO.setRemark(contract.getRemark());
        contractDO.setOrder(contract.getOrder() == null ? null : contract.getOrder().getId());
        contractDO.setStatus(contract.getStatus().name());
        contractDO.setCreator(contract.getCreator().getId());
        contractDO.setEditor(contract.getEditor().getId());
        contractDO.setCreateTime(contract.getCreateTime());
        contractDO.setLastUpdateTime(contract.getLastUpdateTime());
        return contractDO;
    }
}