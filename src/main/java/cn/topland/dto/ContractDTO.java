package cn.topland.dto;

import cn.topland.entity.Contract;
import cn.topland.entity.Order;
import cn.topland.entity.User;
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

    private Long id;

    private String identity;

    private Contract.Type type;

    private Long customer;

    private Long brand;

    @JsonProperty(value = "contract_date")
    private LocalDate contractDate;

    @JsonProperty(value = "contract_date")
    private LocalDate paperDate;

    @JsonProperty(value = "contract_date")
    private LocalDate startDate;

    @JsonProperty(value = "contract_date")
    private LocalDate endDate;

    private BigDecimal margin;

    private BigDecimal guarantee;

    private BigDecimal receivable;

    private User seller;

    private String remark;

    private Order order;

    private List<AttachmentDTO> attachments;

    private Contract.Status status;

    private Long creator;

    private Long editor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}