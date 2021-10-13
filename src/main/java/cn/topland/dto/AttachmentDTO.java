package cn.topland.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AttachmentDTO implements Serializable {

    private Long id;

    private String file;
}