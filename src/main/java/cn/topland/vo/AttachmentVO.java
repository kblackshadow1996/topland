package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AttachmentVO implements Serializable {

    private Long id;

    private String file;
}