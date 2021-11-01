package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.ContractGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.ContractDO;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.ContractVO;
import cn.topland.vo.PaperVO;
import cn.topland.vo.ContractReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DirectusFilesRepository filesRepository;

    @Autowired
    private ContractGateway contractGateway;

    @Autowired
    private OperationGateway operationGateway;

    public Contract get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("合同[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public ContractDO add(ContractVO contractVO, User creator) {

        ContractDO contract = contractGateway.save(createContract(contractVO, creator), creator.getAccessToken());
        saveOperation(contract.getId(), Action.SUBMIT, creator, null);
        return contract;
    }

    public ContractDO review(Long id, ContractReviewVO reviewVO, User editor) {

        Contract contract = get(id);
        ContractDO contractDO = contractGateway.update(reviewContract(contract, reviewVO, editor), editor.getAccessToken());
        saveOperation(id, reviewVO.getAction(), editor, reviewVO.getReviewComment());
        return contractDO;
    }

    public ContractDO receivePaper(Long id, PaperVO paperVO, User creator) {

        Contract contract = get(id);
        return contractGateway.update(receiveContractPaper(contract, paperVO, creator), creator.getAccessToken());
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

    private Contract receiveContractPaper(Contract contract, PaperVO paperVO, User creator) {

        contract.setAttachments(paperVO.getAttachments());
        contract.setPaperDate(paperVO.getPaperDate());
        contract.setEditor(creator);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
    }

    private Contract reviewContract(Contract contract, ContractReviewVO reviewVO, User editor) {

        Action action = reviewVO.getAction();
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

        contract.setOrder(contractVO.getOrder() == null ? null : getOrder(contractVO.getOrder()));
        contract.setCustomer(contractVO.getCustomer() == null ? null : getCustomer(contractVO.getCustomer()));
        contract.setBrand(contractVO.getBrand() == null ? null : getBrand(contractVO.getBrand()));
        contract.setSeller(contractVO.getSeller() == null ? null : getUser(contractVO.getSeller()));
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

    private DirectusFiles getFile(String file) {

        if (file == null || !filesRepository.existsById(file)) {

            throw new QueryException("附件[id:" + file + "]不存在");
        }
        return filesRepository.getById(file);
    }

    private Order getOrder(Long orderId) {

        if (orderId == null || !orderRepository.existsById(orderId)) {

            throw new QueryException("订单[id:" + orderId + "]不存在");
        }
        return orderRepository.getById(orderId);
    }

    private Customer getCustomer(Long customerId) {

        if (customerId == null || !customerRepository.existsById(customerId)) {

            throw new QueryException("客户[id:" + customerId + "]不存在");
        }
        return customerRepository.getById(customerId);
    }

    private Brand getBrand(Long brandId) {

        if (brandId == null || !brandRepository.existsById(brandId)) {

            throw new QueryException("品牌[id:" + brandId + "]不存在");
        }
        return brandRepository.getById(brandId);
    }

    private User getUser(Long userId) {

        if (userId == null || !userRepository.existsById(userId)) {

            throw new QueryException("用户[id:" + userId + "]不存在");
        }
        return userRepository.getById(userId);
    }
}