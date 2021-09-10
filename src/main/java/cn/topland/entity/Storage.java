package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 仓库
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "storage")
public class Storage extends RecordEntity {

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;
}