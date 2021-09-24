package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 异常
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "operation")
public class Exception extends RecordEntity {

    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "type")
    private ExceptionType type;


}