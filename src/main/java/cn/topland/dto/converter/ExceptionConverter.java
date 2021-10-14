package cn.topland.dto.converter;

import cn.topland.dto.ExceptionDTO;
import cn.topland.entity.Exception;
import cn.topland.entity.Order;
import cn.topland.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExceptionConverter extends BaseConverter<Exception, ExceptionDTO> {

    @Autowired
    private AttachmentConverter attachmentConverter;

    @Override
    public List<ExceptionDTO> toDTOs(List<Exception> exceptions) {

        return CollectionUtils.isEmpty(exceptions)
                ? List.of()
                : exceptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ExceptionDTO toDTO(Exception exception) {

        return exception != null
                ? composeException(exception)
                : null;
    }

    private ExceptionDTO composeException(Exception exception) {

        ExceptionDTO dto = new ExceptionDTO();
        dto.setId(exception.getId());
        dto.setAttribute(exception.getAttribute());
        dto.setType(exception.getType());
        dto.setDepartment(getId(exception.getDepartment()));
        dto.setOrders(listOrderIds(exception.getOrders()));
        dto.setOwners(listUserIds(exception.getOwners()));
        dto.setOwners(listUserIds(exception.getOwners()));
        dto.setJudge(getId(exception.getJudge()));
        dto.setComplaint(exception.getComplaint());
        dto.setAttachments(attachmentConverter.toDTOs(exception.getAttachments()));
        dto.setSelfCheck(exception.getSelfCheck());
        dto.setNarrative(exception.getNarrative());
        dto.setEstimatedLoss(exception.getEstimatedLoss());
        dto.setEstimatedLossCondition(exception.getEstimatedLossCondition());
        dto.setCritical(exception.getCritical());
        dto.setStatus(exception.getStatus());
        dto.setCreateDate(exception.getCreateDate());
        dto.setCloseDate(exception.getCloseDate());
        dto.setActualLoss(exception.getActualLoss());
        dto.setActualLossCondition(exception.getActualLossCondition());
        dto.setSolution(exception.getSolution());
        dto.setOptimal(exception.getOptimal());
        dto.setOptimalSolution(exception.getOptimalSolution());

        return dto;
    }

    protected List<Long> listUserIds(List<User> users) {

        return CollectionUtils.isEmpty(users)
                ? List.of()
                : users.stream().map(this::getId).collect(Collectors.toList());
    }

    protected List<Long> listOrderIds(List<Order> orders) {

        return CollectionUtils.isEmpty(orders)
                ? List.of()
                : orders.stream().map(this::getId).collect(Collectors.toList());
    }
}