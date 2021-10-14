package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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
     * 启用状态
     */
    private Boolean active;

    @OneToMany(mappedBy = "type")
    private List<Exception> exceptions;
}