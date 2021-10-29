package cn.topland.dto;

import cn.topland.entity.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ContractDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 编号
     */
    private String identity;

    /**
     * 类型
     */
    private String type;

    /**
     * 客户ID
     */
    private Long customer;

    /**
     * 品牌ID
     */
    private Long brand;

    /**
     * 签约日期：contract_date
     */
    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    /**
     * 纸质合同日期：paper_date
     */
    @JsonProperty(value = "paper_date")
    private LocalDate paperDate;

    /**
     * 开始日期：start_date
     */
    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    /**
     * 结束日期：end_date
     */
    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    /**
     * 保证金
     */
    private BigDecimal margin;

    /**
     * 保底拍摄费用
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
     * 合同-附件中间表ID
     */
    private List<Long> attachments;

    /**
     * 状态
     */
    private String status;

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