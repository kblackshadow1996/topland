package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 审批流程
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "approval")
public class Approval extends RecordEntity {

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 理由
     */
    private String reason;

    public enum Status {

        REVIEWING,
        REJECTED,
        APPROVED
    }
}