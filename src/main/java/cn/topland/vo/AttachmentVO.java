package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AttachmentVO implements Serializable {

    /**
     * 附件id
     */
    private Long id;

    /**
     * 附件文件id
     */
    private String file;
}