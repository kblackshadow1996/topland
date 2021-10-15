package cn.topland.service;

import cn.topland.dao.AttachmentRepository;
import cn.topland.dao.DirectusFilesRepository;
import cn.topland.entity.Attachment;
import cn.topland.entity.DirectusFiles;
import cn.topland.entity.SimpleIdEntity;
import cn.topland.entity.UuidEntity;
import cn.topland.vo.AttachmentVO;
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

    @Transactional
    public List<Attachment> upload(List<AttachmentVO> attachmentVOs) {

        List<String> filedIds = attachmentVOs.stream().map(AttachmentVO::getFile).collect(Collectors.toList());
        Map<String, DirectusFiles> fileMap = filesRepository.findAllById(filedIds).stream()
                .collect(Collectors.toMap(UuidEntity::getId, f -> f));

        Map<Long, Attachment> attachmentMap = repository.findAllById(listAttachmentIds(attachmentVOs)).stream()
                .collect(Collectors.toMap(SimpleIdEntity::getId, a -> a));

        List<Attachment> attachments = attachmentVOs.stream()
                .map(attachmentVO -> {
                    return attachmentVO.getId() != null
                            ? attachmentMap.get(attachmentVO.getId())
                            : createAttachment(fileMap.get(attachmentVO.getFile()));
                })
                .collect(Collectors.toList());
        return repository.saveAllAndFlush(attachments);
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