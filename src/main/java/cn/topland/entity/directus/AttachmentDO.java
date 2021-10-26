package cn.topland.entity.directus;

import cn.topland.entity.Attachment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttachmentDO extends DirectusSimpleIdEntity {

    private String file;

    private Long contract;

    private Long settlement;

    private Long exception;

    public static AttachmentDO from(Attachment attachment) {

        AttachmentDO attachmentDO = new AttachmentDO();
        attachmentDO.setFile(attachment.getFile() == null ? null : attachment.getFile().getId());
        attachmentDO.setContract(attachment.getContract());
        attachmentDO.setSettlement(attachment.getSettlement());
        attachmentDO.setException(attachment.getException());
        return attachmentDO;
    }
}