package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 品牌信息
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand extends RecordEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 销售
     */
    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    /**
     * 制片
     */
    @ManyToOne
    @JoinColumn(name = "production")
    private User production;

    /**
     * 经营信息
     */
    private String business;

    /**
     * 联系人
     */
    @OneToMany(targetEntity = Contact.class)
    @JoinColumn(name = "brand")
    private List<Contact> contacts;

    /**
     * 操作记录
     */
    @OneToMany
    @JoinColumn(name = "brand")
    @OrderBy("createTime desc")
    private List<Operation> operations;

    public enum Action {

        CREATE, // 新建
        UPDATE // 更新
    }
}