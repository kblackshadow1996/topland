package cn.topland.service;

import cn.topland.dao.AttachmentRepository;
import cn.topland.dao.ContractRepository;
import cn.topland.dao.DirectusFilesRepository;
import cn.topland.entity.*;
import cn.topland.vo.AttachmentVO;
import cn.topland.vo.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Contract receivePaper(Long id, ContractVO contractVO, User creator) {

        Contract contract = repository.getById(id);
        // 上传附件
        contract.setAttachments(uploadAttachments(contractVO.getAttachments()));
        contract.setPaperDate(contractVO.getPaperDate());
        contract.setCreator(creator);
        contract.setLastUpdateTime(LocalDateTime.now());

        return repository.saveAndFlush(contract);
    }

    // 关联附件
    private List<Attachment> uploadAttachments(List<AttachmentVO> attachmentVOs) {

        List<String> filedIds = attachmentVOs.stream().map(AttachmentVO::getFile).collect(Collectors.toList());
        Map<String, DirectusFiles> fileMap = filesRepository.findAllById(filedIds).stream()
                .collect(Collectors.toMap(UuidEntity::getId, f -> f));
        List<Attachment> attachments = attachmentVOs.stream()
                .map(attachmentVO -> createAttachment(fileMap.get(attachmentVO.getFile())))
                .collect(Collectors.toList());
        return attachmentRepository.saveAllAndFlush(attachments);
    }

    private Attachment createAttachment(DirectusFiles file) {

        Attachment attachment = new Attachment();
        attachment.setFile(file);
        return attachment;
    }
}