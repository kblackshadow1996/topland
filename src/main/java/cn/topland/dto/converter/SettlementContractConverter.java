package cn.topland.dto.converter;

import cn.topland.dto.SettlementContractDTO;
import cn.topland.entity.SettlementContract;
import cn.topland.entity.directus.SettlementContractDO;
import org.springframework.stereotype.Component;

@Component
public class SettlementContractConverter extends BaseConverter<SettlementContractDO, SettlementContractDTO> {

    @Override
    public SettlementContractDTO toDTO(SettlementContractDO settlementContract) {

        return settlementContract != null
                ? composeSettlementContractDTO(settlementContract)
                : null;
    }

    private SettlementContractDTO composeSettlementContractDTO(SettlementContractDO settlementContract) {

        SettlementContractDTO dto = new SettlementContractDTO();
        dto.setId(settlementContract.getId());
        dto.setIdentity(settlementContract.getIdentity());
        dto.setContractDate(settlementContract.getContractDate());
        dto.setContract(settlementContract.getContract());
        dto.setCreator(settlementContract.getCreator());
        dto.setEditor(settlementContract.getEditor());
        dto.setCreateTime(settlementContract.getCreateTime());
        dto.setLastUpdateTime(settlementContract.getLastUpdateTime());
        dto.setReceivable(settlementContract.getReceivable());
        dto.setRemark(settlementContract.getRemark());
        dto.setStatus(SettlementContract.Status.valueOf(settlementContract.getStatus()));
        dto.setOrder(settlementContract.getOrder());
        dto.setAttachments(settlementContract.getAttachments());
        return dto;
    }
}