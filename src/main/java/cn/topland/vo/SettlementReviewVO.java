package cn.topland.vo;

import cn.topland.entity.SettlementContract;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SettlementReviewVO implements Serializable {

    /**
     * 操作
     */
    private SettlementContract.Action action;

    /**
     * 审核意见
     */
    @JsonProperty("review_comment")
    private String reviewComment;

    /**
     * 操作人ID
     */
    private Long creator;
}