package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.AttachmentGateway;
import cn.topland.dao.gateway.ContractGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.AttachmentDO;
import cn.topland.entity.directus.ContractDO;
import cn.topland.entity.directus.DirectusSimpleIdEntity;
import cn.topland.util.exception.InternalException;
import cn.topland.vo.AttachmentVO;
import cn.topland.vo.ContractVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.topland.entity.Contract.*;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;

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

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ContractGateway contractGateway;

    @Autowired
    private OperationGateway operationGateway;

    @Autowired
    private AttachmentGateway attachmentGateway;

    public Contract get(Long id) {

        return repository.getById(id);
    }

    public ContractDO add(ContractVO contractVO, User creator) throws InternalException {

        ContractDO contract = contractGateway.save(createContract(contractVO, creator), creator.getAccessToken());
        saveOperation(contract.getId(), Action.SUBMIT, creator, null);
        return contract;
    }

    public ContractDO review(Long id, ContractVO contractVO, User editor) throws InternalException {

        Contract contract = repository.getById(id);
        ContractDO contractDO = contractGateway.update(reviewContract(contract, contractVO, editor), editor.getAccessToken());
        saveOperation(id, contractVO.getAction(), editor, contractVO.getReviewComment());
        contractDO.setAttachments(listAttachments(contract.getAttachments()));
        return contractDO;
    }

    public ContractDO receivePaper(Long id, ContractVO contractVO, User creator) throws InternalException {

        Contract contract = repository.getById(id);
        ContractDO contractDO = contractGateway.update(receiveContractPaper(contract, contractVO, creator), creator.getAccessToken());
        saveAllAttachments(contractDO, contractVO.getAttachments(), contract.getAttachments(), creator.getAccessToken());
        return contractDO;
    }

    private List<Long> listAttachments(List<Attachment> attachments) {

        return CollectionUtils.isEmpty(attachments)
                ? List.of()
                : attachments.stream().map(SimpleIdEntity::getId).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void saveAllAttachments(ContractDO contractDO, List<AttachmentVO> attachmentVOs, List<Attachment> attachments, String accessToken) throws InternalException {

        List<Attachment> contractAttachments = createAttachments(attachmentVOs, attachments, contractDO.getId());
        Map<Long, List<AttachmentDO>> attachmentMap = attachmentGateway.upload(contractAttachments, accessToken).stream()
                .filter(attachmentDO -> attachmentDO.getContract() != null)
                .collect(Collectors.groupingBy(AttachmentDO::getContract));
        List<Long> attaches = attachmentMap.get(contractDO.getId()).stream().map(DirectusSimpleIdEntity::getId).collect(Collectors.toList());
        contractDO.setAttachments(attaches);
    }

    public List<Attachment> createAttachments(List<AttachmentVO> attachmentVOs, List<Attachment> attachments, Long contract) {

        List<Long> ids = attachmentVOs.stream().map(AttachmentVO::getId).filter(Objects::nonNull).collect(Collectors.toList());
        Map<Long, Attachment> attachmentMap = attachmentRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(SimpleIdEntity::getId, attachment -> attachment));

        List<Attachment> contractAttachments = new ArrayList<>();
        List<Attachment> updates = new ArrayList<>();
        for (AttachmentVO attachmentVO : attachmentVOs) {

            if (attachmentMap.containsKey(attachmentVO.getId())) {

                Attachment attachment = attachmentMap.get(attachmentVO.getId());
                attachment.setContract(contract);
                contractAttachments.add(attachment);
                updates.add(attachment);
            } else {

                contractAttachments.add(createAttachment(attachmentVO.getFile(), contract));
            }
        }
        List<Attachment> deletes = (List<Attachment>) CollectionUtils.removeAll(attachments, updates);
        deletes.forEach(delete -> {

            delete.setContract(null);
        });
        contractAttachments.addAll(deletes);
        return contractAttachments;
    }

    private Attachment createAttachment(String file, Long contract) {

        Attachment attachment = new Attachment();
        attachment.setFile(filesRepository.getById(file));
        attachment.setContract(contract);
        return attachment;
    }

    private void saveOperation(Long id, Action action, User creator, String remark) throws InternalException {

        operationGateway.save(createOperation(id, action, creator, remark), creator.getAccessToken());
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

    private Contract receiveContractPaper(Contract contract, ContractVO contractVO, User creator) {

        contract.setPaperDate(contractVO.getPaperDate());
        contract.setEditor(creator);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
    }

    private Contract reviewContract(Contract contract, ContractVO contractVO, User editor) {

        Action action = contractVO.getAction();
        Status status = Action.APPROVE == action ? Status.APPROVED : Status.REJECTED;
        contract.setStatus(status);
        contract.setEditor(editor);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
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