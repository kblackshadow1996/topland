package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
     * 创建者id(预留字段,为以后的复杂需求预留空间)
     */
    private Long creatorId;

    /**
     * 修改者id(预留字段,为以后的复杂需求预留空间)
     */
    private Long editorId;
}