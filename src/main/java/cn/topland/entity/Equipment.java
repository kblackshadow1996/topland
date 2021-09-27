package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 器材
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment extends RecordEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 型号
     */
    @ManyToOne
    @JoinColumn(name = "model")
    private Model model;

    /**
     * 存储仓库
     */
    @ManyToOne
    @JoinColumn(name = "storage")
    private Storage storage;

    /**
     * 序列号
     */
    private String identity;

    /**
     * 购入价格
     */
    private BigDecimal price;

    /**
     * 服役时间
     */
    private LocalDate serviceTime;

    /**
     * 状态
     */
    @Enumerated(value = EnumType.STRING)
    private Status status;

    /**
     * 备注
     */
    private String remark;

    public enum Status {

        IN_SERVICE, // 正常
        UNDER_REPAIR, // 维修中
        RETIRED // 退役
    }
}