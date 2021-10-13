package cn.topland.dto.converter;

import cn.topland.dto.ContractDTO;
import cn.topland.entity.Brand;
import cn.topland.entity.Contract;
import cn.topland.entity.Customer;
import cn.topland.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractConverter extends BaseConverter<Contract, ContractDTO> {

    @Autowired
    private AttachmentConverter attachmentConverter;

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
        dto.setAttachments(attachmentConverter.toDTOs(contract.getAttachments()));

        // 关联信息
        dto.setOrder(getOrderId(contract.getOrder()));
        dto.setCustomer(getCustomerId(contract.getCustomer()));
        dto.setBrand(getBrandId(contract.getBrand()));
        dto.setSeller(getUserId(contract.getSeller()));

        // 创建信息
        dto.setCreator(getUserId(contract.getCreator()));
        dto.setEditor(getUserId(contract.getEditor()));
        dto.setCreateTime(contract.getCreateTime());
        dto.setLastUpdateTime(contract.getLastUpdateTime());
        return dto;
    }

    private Long getOrderId(Order order) {

        return order != null
                ? order.getId()
                : null;
    }

    private Long getBrandId(Brand brand) {

        return brand != null
                ? brand.getId()
                : null;
    }

    private Long getCustomerId(Customer customer) {

        return customer != null
                ? customer.getId()
                : null;
    }
}