package cn.topland.service;

import cn.topland.dao.AttachmentRepository;
import cn.topland.dao.DirectusFilesRepository;
import cn.topland.dao.gateway.AttachmentGateway;
import cn.topland.entity.Attachment;
import cn.topland.entity.DirectusFiles;
import cn.topland.entity.Exception;
import cn.topland.entity.SimpleIdEntity;
import cn.topland.entity.UuidEntity;
import cn.topland.entity.directus.AttachmentDO;
import cn.topland.entity.directus.ExceptionDO;
import cn.topland.util.exception.InternalException;
import cn.topland.vo.AttachmentVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository repository;

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private AttachmentGateway attachmentGateway;

    public List<AttachmentDO> uploadContractAttachments(List<AttachmentVO> attachments, Long contract, String token) throws InternalException {

        List<Attachment> attaches = uploadContractAttachments(attachments, contract);
        return attachmentGateway.upload(attaches, token);
    }

    private List<Attachment> uploadContractAttachments(List<AttachmentVO> attachmentVOs, Long contract) {

        List<String> filedIds = attachmentVOs.stream().map(AttachmentVO::getFile).collect(Collectors.toList());
        Map<String, DirectusFiles> fileMap = filesRepository.findAllById(filedIds).stream()
                .collect(Collectors.toMap(UuidEntity::getId, f -> f));

        List<Attachment> attachments = repository.findAllById(listAttachmentIds(attachmentVOs));
        Map<Long, Attachment> attachmentMap = attachments.stream()
                .collect(Collectors.toMap(SimpleIdEntity::getId, a -> a));

        List<Attachment> contractAttachments = new ArrayList<>();
        List<Attachment> updates = new ArrayList<>();
        for (AttachmentVO attachmentVO : attachmentVOs) {

            if (attachmentVO.getId() != null) {

                Attachment attachment = attachmentMap.get(attachmentVO.getId());
                attachment.setContract(contract);
                contractAttachments.add(attachment);
                updates.add(attachment);
            } else {

                Attachment attachment = createAttachment(fileMap.get(attachmentVO.getFile()));
                attachment.setContract(contract);
                contractAttachments.add(attachment);
            }
        }
        List<Attachment> deletes = (List<Attachment>) CollectionUtils.removeAll(attachments, updates);
        deletes.forEach(delete -> {

            // 解除文件关联
            delete.setContract(null);
        });
        contractAttachments.addAll(deletes);
        return contractAttachments;
    }

    private List<Long> listAttachmentIds(List<AttachmentVO> attachmentVOs) {

        List<Long> attachmentIds = new ArrayList<>();
        attachmentVOs.forEach(attachmentVO -> {

            if (attachmentVO.getId() != null) {

                attachmentIds.add(attachmentVO.getId());
            }
        });
        return attachmentIds;
    }

    private Attachment createAttachment(DirectusFiles file) {

        Attachment attachment = new Attachment();
        attachment.setFile(file);
        return attachment;
    }
}