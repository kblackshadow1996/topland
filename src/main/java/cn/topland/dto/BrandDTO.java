package cn.topland.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BrandDTO implements Serializable {

    private Long id;

    private String name;

    private Long seller;

    private Long producer;

    private String business;

    private List<ContactDTO> contracts;
}