package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class PackageVO implements Serializable {

    private String name;

    private List<PackageServiceVO> services;

    private String remark;

    private Long creator;
}