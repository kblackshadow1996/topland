package cn.topland.dto.converter;

import cn.topland.dto.SettlementContractDTO;
import cn.topland.entity.Order;
import cn.topland.entity.SettlementContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettlementContractConverter extends BaseConverter<SettlementContract, SettlementContractDTO> {

    @Autowired
    private AttachmentConverter attachmentConverter;

    @Override
    public SettlementContractDTO toDTO(SettlementContract settlementContract) {

        return settlementContract != null
                ? composeSettlementContractDTO(settlementContract)
                : null;
    }

    private SettlementContractDTO composeSettlementContractDTO(SettlementContract settlementContract) {

        SettlementContractDTO dto = new SettlementContractDTO();
        dto.setId(settlementContract.getId());
        dto.setIdentity(settlementContract.getIdentity());
        dto.setContractDate(settlementContract.getContractDate());
        dto.setCreator(getUserId(settlementContract.getCreator()));
        dto.setReceivable(settlementContract.getReceivable());
        dto.setRemark(settlementContract.getRemark());
        dto.setOrder(getOrder(settlementContract.getOrder()));
        dto.setAttachments(attachmentConverter.toDTOs(settlementContract.getAttachments()));
        return dto;
    }

    private Long getOrder(Order order) {

        return order != null
                ? order.getId()
                : null;
    }
}