package cn.topland.service;

import cn.topland.dao.OperationRepository;
import cn.topland.dao.OrderRepository;
import cn.topland.dao.SettlementContractRepository;
import cn.topland.entity.*;
import cn.topland.vo.SettlementContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.topland.entity.SettlementContract.Action;
import static cn.topland.entity.SettlementContract.Status;

@Service
public class SettlementContractService {

    @Autowired
    private SettlementContractRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Transactional
    public SettlementContract add(SettlementContractVO contractVO, List<Attachment> attachments, User creator) {

        SettlementContract contract = repository.saveAndFlush(createContract(contractVO, attachments, creator));
        saveOperation(contract.getId(), Action.SUBMIT, creator, null);
        return contract;
    }

    @Transactional
    public SettlementContract review(Long id, SettlementContractVO contractVO, User editor) {

        SettlementContract contract = repository.saveAndFlush(reviewSettlementContract(id, contractVO, editor));
        saveOperation(id, contractVO.getAction(), editor, contractVO.getReviewComments());
        return contract;
    }

    private SettlementContract reviewSettlementContract(Long id, SettlementContractVO contractVO, User editor) {

        SettlementContract contract = repository.getById(id);
        Action action = contractVO.getAction();
        Status status = Action.APPROVE == action ? Status.APPROVED : Status.REJECTED;
        contract.setStatus(status);
        contract.setEditor(editor);
        contract.setLastUpdateTime(LocalDateTime.now());
        return contract;
    }

    private SettlementContract createContract(SettlementContractVO contractVO, List<Attachment> attachments, User creator) {

        SettlementContract contract = new SettlementContract();
        contract.setContractDate(contractVO.getContractDate());
        contract.setIdentity(createIdentity(creator));
        contract.setAttachments(attachments);
        contract.setReceivable(contractVO.getReceivable());
        contract.setRemark(contractVO.getRemark());
        contract.setStatus(Status.REVIEWING);
        contract.setOrder(getOrder(contractVO.getOrder()));
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

        operationRepository.saveAndFlush(createOperation(id, action, creator, remark));
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

        return orderId != null
                ? orderRepository.getById(orderId)
                : null;
    }
}