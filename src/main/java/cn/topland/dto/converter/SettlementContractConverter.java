package cn.topland.dto.converter;

import cn.topland.dto.SettlementContractDTO;
import cn.topland.entity.Attachment;
import cn.topland.entity.SettlementContract;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SettlementContractConverter extends BaseConverter<SettlementContract, SettlementContractDTO> {

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
        dto.setContract(getId(settlementContract.getContract()));
        dto.setCreator(getId(settlementContract.getCreator()));
        dto.setReceivable(settlementContract.getReceivable());
        dto.setRemark(settlementContract.getRemark());
        dto.setStatus(settlementContract.getStatus());
        dto.setOrder(getId(settlementContract.getOrder()));
        dto.setAttachments(listAttachmentIds(settlementContract.getAttachments()));
        return dto;
    }

    private List<Long> listAttachmentIds(List<Attachment> attachments) {

        return CollectionUtils.isEmpty(attachments)
                ? List.of()
                : attachments.stream().map(this::getId).collect(Collectors.toList());
    }
}