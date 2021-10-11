package cn.topland.vo;

import java.io.Serializable;
import java.util.List;

public class BrandVO implements Serializable {

    private String name;

    private Long seller;

    private Long producer;

    private String business;

    private List<ContactVO> contracts;
}