package cn.topland.vo;

import cn.topland.entity.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class ContractVO implements Serializable {

    /**
     * 合同类型
     */
    private Contract.Type type;

    /**
     * 客户ID
     */
    private Long customer;

    /**
     * 品牌ID
     */
    private Long brand;

    /**
     * 合同日期
     */
    @JsonProperty(value = "contract_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractDate;

    /**
     * 纸质合同日期：yyyy-MM-dd
     */
    @JsonProperty(value = "paper_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paperDate;

    /**
     * 开始日期：yyyy-MM-dd
     */
    @JsonProperty(value = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 结束日期：yyyy-MM-dd
     */
    @JsonProperty(value = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 保证金
     */
    private BigDecimal margin;

    /**
     * 拍摄保底费用
     */
    private BigDecimal guarantee;

    /**
     * 应收金额
     */
    private BigDecimal receivable;

    /**
     * 销售ID
     */
    private Long seller;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单ID
     */
    private Long order;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 操作人ID
     */
    private Long creator;

    /**
     * 操作
     */
    private Contract.Action action;

    /**
     * 审核意见
     */
    @JsonProperty("review_comment")
    private String reviewComment;
}