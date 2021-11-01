package cn.topland.vo;

import cn.topland.entity.SettlementContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class SettlementContractVO implements Serializable {

    /**
     * 签约日期：yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    /**
     * 应收金额
     */
    private BigDecimal receivable;

    /**
     * 订单ID
     */
    private Long order;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 联系人ID
     */
    private Long contract;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 操作
     */
    private SettlementContract.Action action;
}