package cn.topland.dto.converter;

import cn.topland.dto.ContractDTO;
import cn.topland.entity.Contract;
import cn.topland.entity.directus.ContractDO;
import org.springframework.stereotype.Component;

@Component
public class ContractConverter extends BaseConverter<ContractDO, ContractDTO> {

    @Override
    public ContractDTO toDTO(ContractDO contract) {

        return contract != null
                ? composeContractDTO(contract)
                : null;
    }

    private ContractDTO composeContractDTO(ContractDO contract) {

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
        dto.setStatus(Contract.Status.valueOf(contract.getStatus()));
        dto.setType(Contract.Type.valueOf(contract.getType()));
        dto.setRemark(contract.getRemark());
        dto.setAttachments(contract.getAttachments());

        // 关联信息
        dto.setOrder(contract.getOrder());
        dto.setCustomer(contract.getCustomer());
        dto.setBrand(contract.getBrand());
        dto.setSeller(contract.getSeller());

        // 创建信息
        dto.setCreator(contract.getCreator());
        dto.setEditor(contract.getEditor());
        dto.setCreateTime(contract.getCreateTime());
        dto.setLastUpdateTime(contract.getLastUpdateTime());
        return dto;
    }
}