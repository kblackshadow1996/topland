package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 异常
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exception")
public class Exception extends RecordEntity {

    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "type")
    private ExceptionType type;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 处理
     */
    @OneToOne
    @JoinColumn(name = "solution")
    private Solution solution;

    public enum Status {

        RESOLVED, // 已解决
        UNRESOLVED // 待处理
    }
}