package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class WeworkLoginVO implements Serializable {

    private String code;

    private String state;
}