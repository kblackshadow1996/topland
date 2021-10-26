package cn.topland.dto.converter;

import cn.topland.dto.ExceptionDTO;
import cn.topland.entity.directus.ExceptionDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExceptionConverter extends BaseConverter<ExceptionDO, ExceptionDTO> {

    @Override
    public List<ExceptionDTO> toDTOs(List<ExceptionDO> exceptions) {

        return CollectionUtils.isEmpty(exceptions)
                ? List.of()
                : exceptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ExceptionDTO toDTO(ExceptionDO exception) {

        return exception != null
                ? composeException(exception)
                : null;
    }

    private ExceptionDTO composeException(ExceptionDO exception) {

        ExceptionDTO dto = new ExceptionDTO();
        dto.setId(exception.getId());
        dto.setAttribute(exception.getAttribute());
        dto.setType(exception.getType());
        dto.setDepartment(exception.getDepartment());
        dto.setOrders(exception.getOrders());
        dto.setOwners(exception.getOwners());
        dto.setOwners(exception.getOwners());
        dto.setJudge(exception.getJudge());
        dto.setComplaint(exception.getComplaint());
        dto.setAttachments(exception.getAttachments());
        dto.setSelfCheck(exception.getSelfCheck());
        dto.setNarrative(exception.getNarrative());
        dto.setEstimatedLoss(exception.getEstimatedLoss());
        dto.setEstimatedLossCondition(exception.getEstimatedLossCondition());
        dto.setCritical(exception.getCritical());
        dto.setResolved(exception.getResolved());
        dto.setCreateDate(exception.getCreateDate());
        dto.setCloseDate(exception.getCloseDate());
        dto.setActualLoss(exception.getActualLoss());
        dto.setActualLossCondition(exception.getActualLossCondition());
        dto.setSolution(exception.getSolution());
        dto.setOptimal(exception.getOptimal());
        dto.setOptimalSolution(exception.getOptimalSolution());

        return dto;
    }
}