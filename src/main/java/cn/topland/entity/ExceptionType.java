package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 异常类型
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exception_type")
public class ExceptionType extends SimpleIdEntity {

    /**
     * 类型名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最优解决方案
     */
    private String solution;

    /**
     * 启用状态
     */
    private Boolean active;
}