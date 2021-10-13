package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.entity.*;
import cn.topland.vo.AttachmentVO;
import cn.topland.vo.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.topland.entity.Contract.*;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Transactional
    public Contract add(ContractVO contractVO, User creator) {

        Contract contract = repository.saveAndFlush(createContract(contractVO, creator));
        saveOperation(contract.getId(), Action.SUBMIT, creator, null);
        return contract;
    }

    @Transactional
    public Contract review(Long id, ContractVO contractVO, User editor) {

        Contract contract = repository.getById(id);
        Action action = contractVO.getAction();
        Status status = Action.APPROVE == action ? Status.APPROVED : Status.REJECTED;
        contract.setStatus(status);
        saveOperation(id, action, editor, contractVO.getRemark());
        return repository.saveAndFlush(contract);
    }

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

    private void saveOperation(Long id, Action action, User creator, String remark) {

        operationRepository.saveAndFlush(createOperation(id, action, creator, remark));
    }

    private Operation createOperation(Long id, Action action, User creator, String remark) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.CONTRACT);
        operation.setAction(action.name());
        operation.setModuleId(String.valueOf(id));
        operation.setRemark(remark);
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private Contract createContract(ContractVO contractVO, User creator) {

        Contract contract = new Contract();
        Type type = contractVO.getType();
        contract.setType(type);
        contract.setIdentity(createIdentity(type, creator));
        contract.setContractDate(contractVO.getContractDate());
        contract.setStartDate(contractVO.getStartDate());
        contract.setEndDate(contractVO.getEndDate());
        contract.setReceivable(contractVO.getReceivable());
        contract.setMargin(contractVO.getMargin());
        contract.setGuarantee(contractVO.getGuarantee());
        contract.setStatus(Status.REVIEWING); // 新建合同则为审核中
        contract.setRemark(contractVO.getRemark());

        contract.setOrder(getOrder(contractVO.getOrder()));
        contract.setCustomer(getCustomer(contractVO.getCustomer()));
        contract.setBrand(getBrand(contractVO.getBrand()));
        contract.setSeller(getUser(contractVO.getSeller()));
        contract.setCreator(creator);
        contract.setEditor(creator);
        return contract;
    }

    private String createIdentity(Type type, User creator) {

        // 该用户当天生成合同的数量
        Long count = repository.countByCreatorAndCreateTimeBetween(creator,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        // 当日时间：年月日如20211212
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 工号
        String employeeId = creator.getEmployeeId();
        String prefix = Type.YEAR == type ? "Y" : "O";
        // 类型+时间+工号+第n份合同
        return prefix + date + employeeId + (count + 1);
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

    private Order getOrder(Long orderId) {

        return orderId != null
                ? orderRepository.getById(orderId)
                : null;
    }

    private Customer getCustomer(Long customerId) {

        return customerId != null
                ? customerRepository.getById(customerId)
                : null;
    }

    private Brand getBrand(Long brandId) {

        return brandId != null
                ? brandRepository.getById(brandId)
                : null;
    }

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }
}