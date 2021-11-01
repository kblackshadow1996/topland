package cn.topland.entity.directus;

import cn.topland.entity.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String attachments;

    private String status;

    @Setter
    @Getter
    public static class BaseContract {

        private String identity;

        private String type;

        private Long customer;

        private Long brand;

        @JsonProperty(value = "contract_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate contractDate;

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

        private String status;

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
    public static class Paper {

        @JsonProperty(value = "paper_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate paperDate;

        private String attachments;

        @JsonProperty(value = "last_update_time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastUpdateTime;

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

    public static Review review(Contract contract) {

        Review review = new Review();
        review.setStatus(contract.getStatus().name());
        review.setEditor(contract.getEditor().getId());
        review.setLastUpdateTime(contract.getLastUpdateTime());
        return review;
    }

    public static Paper paper(Contract contract) {

        Paper paper = new Paper();
        paper.setPaperDate(contract.getPaperDate());
        paper.setAttachments(contract.getAttachments());
        paper.setLastUpdateTime(contract.getLastUpdateTime());
        paper.setEditor(contract.getEditor().getId());
        return paper;
    }

    public static BaseContract from(Contract contract) {

        BaseContract base = new BaseContract();
        base.setIdentity(contract.getIdentity());
        base.setType(contract.getType().name());
        base.setCustomer(contract.getCustomer() == null ? null : contract.getCustomer().getId());
        base.setBrand(contract.getBrand() == null ? null : contract.getBrand().getId());
        base.setContractDate(contract.getContractDate());
        base.setStartDate(contract.getStartDate());
        base.setEndDate(contract.getEndDate());
        base.setMargin(contract.getMargin());
        base.setGuarantee(contract.getGuarantee());
        base.setReceivable(contract.getReceivable());
        base.setSeller(contract.getSeller() == null ? null : contract.getSeller().getId());
        base.setRemark(contract.getRemark());
        base.setOrder(contract.getOrder() == null ? null : contract.getOrder().getId());
        base.setStatus(contract.getStatus().name());
        base.setCreator(contract.getCreator().getId());
        base.setEditor(contract.getEditor().getId());
        base.setCreateTime(contract.getCreateTime());
        base.setLastUpdateTime(contract.getLastUpdateTime());
        return base;
    }
}