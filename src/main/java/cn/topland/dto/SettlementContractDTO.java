package cn.topland.dto;

import cn.topland.entity.SettlementContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class SettlementContractDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 编号
     */
    private String identity;

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
     * 状态
     */
    private SettlementContract.Status status;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 年框合同ID
     */
    private Long contract;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 修改人ID
     */
    private Long editor;

    /**
     * 创建时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间：yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}