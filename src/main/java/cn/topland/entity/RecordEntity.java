package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * 记录:
 * 创建人、创建时间、更新时间
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class RecordEntity extends IdEntity {

    /**
     * 创建者(预留字段,为以后的复杂需求预留空间)
     */
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    /**
     * 修改者(预留字段,为以后的复杂需求预留空间)
     */
    @ManyToOne
    @JoinColumn(name = "editor")
    private User editor;
}