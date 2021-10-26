package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.ExceptionGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.Exception;
import cn.topland.entity.*;
import cn.topland.entity.directus.ExceptionDO;
import cn.topland.util.exception.InternalException;
import cn.topland.vo.AttachmentVO;
import cn.topland.vo.ExceptionVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.topland.entity.Exception.Action;

@Service
public class ExceptionService {

    @Autowired
    private ExceptionRepository repository;

    @Autowired
    private ExceptionTypeRepository typeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DirectusFilesRepository filesRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ExceptionGateway exceptionGateway;

    @Autowired
    private OperationGateway operationGateway;

    public Exception get(Long id) {

        return repository.getById(id);
    }

    public List<Exception> createAll(List<ExceptionVO> exceptionVOs, User creator) {

        return createExceptions(exceptionVOs, creator);
    }

    @Transactional
    public List<ExceptionDO> add(List<Exception> exceptions, User creator) throws InternalException {

        List<ExceptionDO> exceptionDOs = exceptionGateway.saveAll(exceptions, creator.getAccessToken());
        saveCreateOperations(exceptionDOs, creator);
        return exceptionDOs;
    }

    @Transactional
    public ExceptionDO update(Long id, ExceptionVO exceptionVO, User editor) throws InternalException {

        Exception exception = repository.getById(id);
        ExceptionDO exceptionDO = exceptionGateway.update(updateException(exception, exceptionVO, editor), editor.getAccessToken());
        saveUpdateOperation(id, editor);
        return exceptionDO;
    }

    @Transactional
    public ExceptionDO solve(Long id, ExceptionVO exceptionVO, User editor) throws InternalException {

        Exception persistExp = repository.getById(id);
        boolean isUpdate = isUpdateSolution(persistExp);
        ExceptionDO exception = exceptionGateway.update(solveException(persistExp, exceptionVO, editor), editor.getAccessToken());
        saveSolveOperation(id, editor, isUpdate);
        return exception;
    }

    private Exception solveException(Exception exception, ExceptionVO exceptionVO, User editor) {

        exception.setCloseDate(exceptionVO.getCloseDate());
        exception.setSolution(exceptionVO.getSolution());
        exception.setActualLoss(exceptionVO.getActualLoss());
        exception.setActualLossCondition(exceptionVO.getActualLossCondition());
        exception.setOptimal(exceptionVO.getOptimal());
        exception.setOptimalSolution(exceptionVO.getOptimalSolution());
        exception.setResolved(true);
        exception.setEditor(editor);
        exception.setLastUpdateTime(LocalDateTime.now());
        return exception;
    }

    private Exception updateException(Exception exception, ExceptionVO exceptionVO, User editor) {

        composeException(exception, exceptionVO);
        exception.setEditor(editor);
        exception.setLastUpdateTime(LocalDateTime.now());
        return exception;
    }

    private List<Exception> createExceptions(List<ExceptionVO> exceptionVOs, User creator) {

        List<Exception> exceptions = exceptionVOs.stream()
                .map(vo -> createException(vo, listExceptionAttachments(vo.getAttachments()), creator))
                .collect(Collectors.toList());
        checkIfCritical(exceptions);
        return exceptions;
    }

    private Exception createException(ExceptionVO exceptionVO, List<Attachment> attachments, User creator) {

        Exception exception = new Exception();
        composeException(exception, exceptionVO);
        exception.setAttachments(attachments);
        exception.setCreator(creator);
        exception.setEditor(creator);
        return exception;
    }

    private void composeException(Exception exception, ExceptionVO exceptionVO) {

        exception.setCreateDate(exceptionVO.getCreateDate());
        exception.setOrders(listOrders(exceptionVO.getOrders()));
        exception.setAttribute(exceptionVO.getAttribute());
        exception.setType(getType(exceptionVO.getType()));
        exception.setCreateDate(exceptionVO.getCreateDate());
        exception.setDepartment(getDepartment(exceptionVO.getDepartment()));
        exception.setOwners(listUsers(exceptionVO.getOwners()));
        exception.setCopies(listUsers(exceptionVO.getCopies()));
        exception.setJudge(getUser(exceptionVO.getJudge()));
        exception.setComplaint(exceptionVO.getComplaint());
        exception.setSelfCheck(exceptionVO.getSelfCheck());
        exception.setNarrative(exceptionVO.getNarrative());
        exception.setEstimatedLoss(exceptionVO.getEstimatedLoss());
        exception.setEstimatedLossCondition(exceptionVO.getEstimatedLossCondition());
    }

    private void checkIfCritical(List<Exception> exceptions) {

        // 1. 根据预估金额判断
        checkIfCriticalByEstimatedLoss(exceptions);
        // 2. 根据选择类型判断, 第4条及以后异常
        checkIfCriticalByType(exceptions);
        // 3. 根据订单异常数量判断
        checkIfCriticalByOrder(exceptions);
        // 4. 根据品牌录入同一异常类型数量
        checkIfCriticalByBrandAndType(exceptions);
    }

    private void checkIfCriticalByBrandAndType(List<Exception> exceptions) {

        exceptions.forEach(exception -> {

            if (CollectionUtils.isNotEmpty(exception.getOrders())) {

                exception.getOrders().stream()
                        .max(Comparator.comparingLong(order -> countByBrandAndType(order.getBrand(), exception.getType())))
                        .ifPresent(order -> {

                            if (countByBrandAndType(order.getBrand(), exception.getType()) + exceptions.indexOf(exception) > 1) {

                                exception.setCritical(true);
                            }
                        });
            }
        });
    }

    private long countByBrandAndType(Brand brand, ExceptionType type) {

        if (brand != null && CollectionUtils.isNotEmpty(brand.getOrders())) {

            return brand.getOrders().stream().mapToLong(order -> {

                return order.getExceptions().stream()
                        .filter(exception -> type.equals(exception.getType()))
                        .count();
            }).sum();
        }
        return 0;
    }

    private void checkIfCriticalByOrder(List<Exception> exceptions) {

        exceptions.forEach(exception -> {

            if (CollectionUtils.isNotEmpty(exception.getOrders())) {

                exception.getOrders().stream()
                        .max(Comparator.comparingInt(order -> order.getExceptions().size()))
                        .ifPresent(order -> {

                            if (order.getExceptions().size() + exceptions.indexOf(exception) > 1) {

                                exception.setCritical(true);
                            }
                        });
            }
        });
    }

    private void checkIfCriticalByType(List<Exception> exceptions) {

        exceptions.forEach(exception -> {

            if (exception.getType() != null && exception.getType().getExceptions().size() + exceptions.indexOf(exception) > 2) {

                exception.setCritical(true);
            }
        });
    }

    private void checkIfCriticalByEstimatedLoss(List<Exception> exceptions) {

        exceptions.forEach(exception -> {

            if (exception.getEstimatedLoss() != null && exception.getEstimatedLoss().compareTo(new BigDecimal("1000.00")) > 0) {

                exception.setCritical(true);
            }
        });
    }

    private void saveCreateOperations(List<ExceptionDO> exceptions, User creator) throws InternalException {

        operationGateway.saveAll(createAddExceptionOperations(exceptions, creator), creator.getAccessToken());
    }

    private void saveUpdateOperation(Long id, User editor) throws InternalException {

        operationGateway.save(createOperation(Action.UPDATE, id, editor), editor.getAccessToken());
    }

    private void saveSolveOperation(Long id, User editor, boolean isUpdate) throws InternalException {

        Action action = isUpdate ? Action.UPDATE_SOLUTION : Action.CREATE_SOLUTION;
        operationGateway.save(createOperation(action, id, editor), editor.getAccessToken());
    }

    // 是否更新异常处理
    private boolean isUpdateSolution(Exception exception) {

        return exception.getCloseDate() != null;
    }

    private List<Operation> createAddExceptionOperations(List<ExceptionDO> exceptions, User creator) {

        return exceptions.stream()
                .map(exception -> createOperation(Action.CREATE, exception.getId(), creator))
                .collect(Collectors.toList());
    }

    private Operation createOperation(Action action, Long id, User creator) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.EXCEPTION);
        operation.setAction(action.name());
        operation.setModuleId(String.valueOf(id));
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private List<Attachment> listExceptionAttachments(List<AttachmentVO> attachmentVOs) {

        return CollectionUtils.isNotEmpty(attachmentVOs)
                ? attachmentVOs.stream()
                .map(attachmentVO -> {

                    Attachment attachment = new Attachment();
                    attachment.setFile(filesRepository.getById(attachmentVO.getFile()));
                    return attachment;
                })
                .collect(Collectors.toList())
                : List.of();
    }

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }

    private List<User> listUsers(List<Long> userIds) {

        return CollectionUtils.isEmpty(userIds)
                ? List.of()
                : userRepository.findAllById(userIds);
    }

    private List<Order> listOrders(List<Long> orderIds) {

        return CollectionUtils.isEmpty(orderIds)
                ? List.of()
                : orderRepository.findAllById(orderIds);
    }

    private Department getDepartment(Long department) {

        return department != null
                ? departmentRepository.getById(department)
                : null;
    }

    private ExceptionType getType(Long type) {

        return type != null
                ? typeRepository.getById(type)
                : null;
    }
}