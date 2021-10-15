package cn.topland.dto.converter;

import cn.topland.dto.ContractDTO;
import cn.topland.entity.Attachment;
import cn.topland.entity.Contract;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractConverter extends BaseConverter<Contract, ContractDTO> {

    @Override
    public ContractDTO toDTO(Contract contract) {

        return contract != null
                ? composeContractDTO(contract)
                : null;
    }

    private ContractDTO composeContractDTO(Contract contract) {

        // 基本信息
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setIdentity(contract.getIdentity());
        dto.setContractDate(contract.getContractDate());
        dto.setPaperDate(contract.getPaperDate());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setReceivable(contract.getReceivable());
        dto.setMargin(contract.getMargin());
        dto.setGuarantee(contract.getGuarantee());
        dto.setStatus(contract.getStatus());
        dto.setType(contract.getType());
        dto.setRemark(contract.getRemark());
        dto.setAttachments(listAttachmentIds(contract.getAttachments()));

        // 关联信息
        dto.setOrder(getId(contract.getOrder()));
        dto.setCustomer(getId(contract.getCustomer()));
        dto.setBrand(getId(contract.getBrand()));
        dto.setSeller(getId(contract.getSeller()));

        // 创建信息
        dto.setCreator(getId(contract.getCreator()));
        dto.setEditor(getId(contract.getEditor()));
        dto.setCreateTime(contract.getCreateTime());
        dto.setLastUpdateTime(contract.getLastUpdateTime());
        return dto;
    }

    private List<Long> listAttachmentIds(List<Attachment> attachments) {

        return CollectionUtils.isEmpty(attachments)
                ? List.of()
                : attachments.stream().map(this::getId).collect(Collectors.toList());
    }
}