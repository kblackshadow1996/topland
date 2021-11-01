package cn.topland.entity.directus;

import cn.topland.entity.SettlementContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String attachments;

    private String status;

    private Long contract;

    @Setter
    @Getter
    public static class BaseSettlement {

        private String identity;

        @JsonProperty(value = "contract_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate contractDate;

        private BigDecimal receivable;

        private Long order;

        private String remark;

        private String attachments;

        private String status;

        private Long contract;

        @JsonProperty(value = "create_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createTime;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long creator;

        private Long editor;
    }

    @Setter
    @Getter
    public static class Review {

        private String status;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

        private Long editor;
    }

    public static BaseSettlement from(SettlementContract contract) {

        BaseSettlement base = new BaseSettlement();
        base.setAttachments(contract.getAttachments());
        base.setIdentity(contract.getIdentity());
        base.setContractDate(contract.getContractDate());
        base.setReceivable(contract.getReceivable());
        base.setOrder(contract.getOrder() == null ? null : contract.getOrder().getId());
        base.setRemark(contract.getRemark());
        base.setStatus(contract.getStatus().name());
        base.setContract(contract.getContract() == null ? null : contract.getContract().getId());
        base.setCreator(contract.getCreator().getId());
        base.setEditor(contract.getEditor().getId());
        base.setCreateTime(contract.getCreateTime());
        base.setLastUpdateTime(contract.getLastUpdateTime());
        return base;
    }

    public static Review review(SettlementContract contract) {

        Review review = new Review();
        review.setStatus(contract.getStatus().name());
        review.setEditor(contract.getEditor().getId());
        review.setLastUpdateTime(contract.getLastUpdateTime());
        return review;
    }
}