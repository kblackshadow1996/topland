package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.dao.gateway.SettlementContractGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.SettlementContractDO;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.SettlementContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static cn.topland.entity.SettlementContract.Action;
import static cn.topland.entity.SettlementContract.Status;

@Service
public class SettlementContractService {

    @Autowired
    private SettlementContractRepository repository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private OperationGateway operationGateway;

    @Autowired
    private SettlementContractGateway settlementGateway;

    public SettlementContract get(Long id) {

        if (!repository.existsById(id)) {

            throw new QueryException("报价合同[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public SettlementContractDO add(SettlementContractVO contractVO, User creator) {

        SettlementContractDO contractDO = settlementGateway.save(createContract(contractVO, creator), creator.getAccessToken());
        saveOperation(contractDO.getId(), Action.SUBMIT, creator, null);
        return contractDO;
    }

    public SettlementContractDO review(Long id, SettlementContractVO contractVO, User editor) {

        SettlementContract contract = get(id);
        SettlementContractDO contractDO = settlementGateway.update(reviewSettlementContract(contract, contractVO, editor), editor.getAccessToken());
        saveOperation(id, contractVO.getAction(), editor, contractVO.getReviewComments());
        return contractDO;
    }

    private SettlementContract reviewSettlementContract(SettlementContract contract, SettlementContractVO contractVO, User editor) {

        Action action = contractVO.getAction();
        Status status = Action.APPROVE == action ? Status.APPROVED : Status.REJECTED;
        contract.setStatus(status);
        contract.setEditor(editor);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
    }

    private SettlementContract createContract(SettlementContractVO contractVO, User creator) {

        SettlementContract contract = new SettlementContract();
        contract.setContractDate(contractVO.getContractDate());
        contract.setAttachments(contractVO.getAttachments());
        contract.setIdentity(createIdentity(creator));
        contract.setContract(contractVO.getContract() == null ? null : getContract(contractVO.getContract()));
        contract.setReceivable(contractVO.getReceivable());
        contract.setRemark(contractVO.getRemark());
        contract.setStatus(Status.REVIEWING);
        contract.setOrder(contractVO.getOrder() == null ? null : getOrder(contractVO.getOrder()));
        contract.setCreator(creator);
        contract.setEditor(creator);
        return contract;
    }

    private String createIdentity(User creator) {

        // 该用户当天生成合同的数量
        Long count = repository.countByCreatorAndCreateTimeBetween(creator,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        // 当日时间：年月日如20211212
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 工号
        String employeeId = creator.getEmployeeId();
        // 类型+时间+工号+第n份合同
        return "S" + date + employeeId + (count + 1);
    }

    private void saveOperation(Long id, Action action, User creator, String remark) {

        operationGateway.save(createOperation(id, action, creator, remark), creator.getAccessToken());
    }

    private Operation createOperation(Long id, Action action, User creator, String remark) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.SETTLEMENT);
        operation.setAction(action.name());
        operation.setModuleId(String.valueOf(id));
        operation.setRemark(remark);
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private Order getOrder(Long orderId) {

        if (orderId == null || !orderRepository.existsById(orderId)) {

            throw new QueryException("订单[id:" + orderId + "]不存在");
        }
        return orderRepository.getById(orderId);
    }

    private Contract getContract(Long contract) {

        if (contract == null || !contractRepository.existsById(contract)) {

            throw new QueryException("年框合同[id:" + contract + "不存在]");
        }
        return contractRepository.getById(contract);
    }
}