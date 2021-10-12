package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BrandVO implements Serializable {

    private Long id;

    private String name;

    private Long customer;

    private Long seller;

    private Long producer;

    private String business;

    private List<ContactVO> contacts;
}