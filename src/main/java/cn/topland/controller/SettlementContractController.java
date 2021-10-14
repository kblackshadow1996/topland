package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.SettlementContractConverter;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.SettlementContractService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.SettlementContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settlement-contract")
public class SettlementContractController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private SettlementContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private SettlementContractConverter settlementContractConverter;

    @PostMapping("/add")
    public Response add(@RequestBody SettlementContractVO contractVO) {

        try {

            User user = userService.get(contractVO.getCreator());
            validator.validateSettlementCreatePermissions(user.getRole());
            return Responses.success(settlementContractConverter.toDTO(
                    contractService.add(contractVO, attachmentService.upload(contractVO.getAttachments()), user)
            ));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/review/{id}")
    public Response review(@PathVariable Long id, @RequestBody SettlementContractVO contractVO) {

        try {

            User user = userService.get(contractVO.getCreator());
            validator.validateSettlementCreatePermissions(user.getRole());
            return Responses.success(settlementContractConverter.toDTO(
                    contractService.review(id, contractVO, user)
            ));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}