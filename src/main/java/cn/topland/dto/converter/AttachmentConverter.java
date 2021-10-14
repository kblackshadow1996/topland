package cn.topland.dto.converter;

import cn.topland.dto.AttachmentDTO;
import cn.topland.entity.Attachment;
import cn.topland.entity.DirectusFiles;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttachmentConverter extends BaseConverter<Attachment, AttachmentDTO> {

    @Override
    public List<AttachmentDTO> toDTOs(List<Attachment> attachments) {

        return CollectionUtils.isEmpty(attachments)
                ? List.of()
                : attachments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public AttachmentDTO toDTO(Attachment attachment) {

        return attachment != null
                ? composeAttachmentDTO(attachment)
                : null;
    }

    private AttachmentDTO composeAttachmentDTO(Attachment attachment) {

        AttachmentDTO dto = new AttachmentDTO();
        dto.setId(attachment.getId());
        dto.setFile(getId(attachment.getFile()));
        return dto;
    }
}
