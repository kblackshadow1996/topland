package cn.topland.dao.gateway;

import cn.topland.entity.Attachment;
import cn.topland.entity.directus.AttachmentDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.Reply;
import cn.topland.util.exception.InternalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttachmentGateway extends BaseGateway {

    @Value("${directus.items.attachment}")
    private String ATTACHMENT_URI;

    @Autowired
    private DirectusGateway directus;

    private static final TypeReference<List<AttachmentDO>> ATTACHMENTS = new TypeReference<>() {
    };

    public List<AttachmentDO> upload(List<Attachment> attachments, String accessToken) throws InternalException {

        List<AttachmentDO> attachmentDOs = createAttachments(listCreateAttachments(attachments), accessToken);
        attachmentDOs.addAll(updateAttachments(listUpdateAttachments(attachments), accessToken));
        return attachmentDOs;
    }

    private List<AttachmentDO> updateAttachments(List<Attachment> attachments, String accessToken) throws InternalException {

        List<AttachmentDO> attachmentDOs = new ArrayList<>();
        for (Attachment attachment : attachments) {

            Reply result = directus.patch(ATTACHMENT_URI + "/" + attachment.getId(), tokenParam(accessToken),
                    JsonUtils.toJsonNode(AttachmentDO.from(attachment)));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                attachmentDOs.add(JsonUtils.parse(data, AttachmentDO.class));
            } else {

                throw new InternalException("更新附件失败");
            }
        }
        return attachmentDOs;
    }

    private List<AttachmentDO> createAttachments(List<Attachment> attachments, String accessToken) throws InternalException {

        if (CollectionUtils.isNotEmpty(attachments)) {

            Reply result = directus.post(ATTACHMENT_URI, tokenParam(accessToken), composeAttachments(attachments));
            if (result.isSuccessful()) {

                String data = JsonUtils.read(result.getContent()).path("data").toPrettyString();
                return JsonUtils.parse(data, ATTACHMENTS);
            } else {

                throw new InternalException("上传文件失败");
            }
        }
        return new ArrayList<>();
    }


    private List<AttachmentDO> convertToDOs(List<Attachment> attachments) {

        return attachments.stream().map(attachment -> {

            AttachmentDO attachmentDO = AttachmentDO.from(attachment);
            attachmentDO.setId(attachment.getId());
            return attachmentDO;
        }).collect(Collectors.toList());
    }

    private JsonNode composeAttachments(List<Attachment> attachments) {

        List<AttachmentDO> attachmentDOs = attachments.stream().map(AttachmentDO::from).collect(Collectors.toList());
        return JsonUtils.toJsonNode(attachmentDOs);
    }

    private List<Attachment> listCreateAttachments(List<Attachment> attachments) {

        return attachments.stream().filter(attachment -> attachment.getId() == null).collect(Collectors.toList());
    }

    private List<Attachment> listUpdateAttachments(List<Attachment> attachments) {

        return attachments.stream().filter(attachment -> attachment.getId() != null).collect(Collectors.toList());
    }
}