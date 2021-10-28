package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class PackageVO implements Serializable {

    /**
     * 名称
     */
    private String name;

    private List<PackageServiceVO> services;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long creator;
}