package cn.topland.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 组织
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "department")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Department extends RecordEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 组织id
     */
    private String deptId;

    /**
     * 父组织id
     */
    @ManyToOne
    @JoinColumn(name = "parent")
    private Department parent;

    @Transient
    private String parentDeptId;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * 来源
     */
    @Enumerated(EnumType.STRING)
    private Source source;

    public enum Source {

        WEWORK, // 企业微信
        SUPPLIER, // 供应商
        OTHER
    }

    public enum Type {

        COMPANY, // 公司
        BRANCH, // 分公司
        DEPARTMENT // 部门
    }
}