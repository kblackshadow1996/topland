package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.ContractGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.ContractDO;
import cn.topland.util.exception.InternalException;
import cn.topland.vo.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private ContractGateway contractGateway;

    @Autowired
    private OperationGateway operationGateway;

    public Contract get(Long id) {

        return repository.getById(id);
    }

    @Transactional
    public ContractDO add(ContractVO contractVO, User creator) throws InternalException {

        ContractDO contract = contractGateway.save(createContract(contractVO, creator), creator.getAccessToken());
        saveOperation(contract.getId(), Action.SUBMIT, creator, null);
        return contract;
    }

    @Transactional
    public ContractDO review(Long id, ContractVO contractVO, User editor) throws InternalException {

        ContractDO contract = contractGateway.update(reviewContract(id, contractVO, editor), editor.getAccessToken());
        saveOperation(id, contractVO.getAction(), editor, contractVO.getReviewComment());
        return contract;
    }

    @Transactional
    public ContractDO receivePaper(Long id, ContractVO contractVO, User creator) throws InternalException {

        return contractGateway.update(receiveContractPaper(id, contractVO, creator), creator.getAccessToken());
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

    private Contract receiveContractPaper(Long id, ContractVO contractVO, User creator) {

        Contract contract = repository.getById(id);
        contract.setPaperDate(contractVO.getPaperDate());
        contract.setEditor(creator);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
    }

    private Contract reviewContract(Long id, ContractVO contractVO, User editor) {

        Contract contract = repository.getById(id);
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