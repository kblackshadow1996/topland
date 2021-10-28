package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BrandVO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 客户ID
     */
    private Long customer;

    /**
     * 销售ID
     */
    private Long seller;

    /**
     * 制片ID
     */
    private Long producer;

    /**
     * 经营信息
     */
    private String business;

    private List<ContactVO> contacts;

    /**
     * 操作人ID
     */
    private Long creator;
}