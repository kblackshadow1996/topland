package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.ContractConverter;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.ContractService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private ContractConverter contractConverter;

    @PostMapping("/add")
    public Response add(@RequestBody ContractVO contractVO) {

        try {

            User user = userService.get(contractVO.getCreator());
            validator.validateContractCreatePermissions(user.getRole());
            return Responses.success(contractConverter.toDTO(contractService.add(contractVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/receive-paper/{id}")
    public Response receivePaper(@PathVariable Long id, @RequestBody ContractVO contractVO) {

        try {

            User user = userService.get(contractVO.getCreator());
            validator.validateContractReceivePaperPermissions(user.getRole());
            return Responses.success(contractConverter.toDTO(
                    contractService.receivePaper(id, contractVO, attachmentService.upload(contractVO.getAttachments()), user)
            ));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/review/{id}")
    public Response review(@PathVariable Long id, @RequestBody ContractVO contractVO) {

        try {

            User user = userService.get(contractVO.getCreator());
            validator.validateContractReviewPermissions(user.getRole());
            return Responses.success(contractConverter.toDTO(contractService.review(id, contractVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}