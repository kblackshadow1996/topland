package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 报价备注
 */
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class QuotationComment implements Serializable {

    /**
     * 小计备注
     */
    private String subtotalComment;

    /**
     * 优惠备注
     */
    private String discountComment;

    /**
     * 未税总计备注
     */
    private String totalComment;
}