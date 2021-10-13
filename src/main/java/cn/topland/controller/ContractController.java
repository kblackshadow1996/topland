package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.entity.User;
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
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @PatchMapping("/{id}/receive-paper")
    public Response receivePaper(@PathVariable Long id, @RequestBody ContractVO contractVO) {

        User user = userService.get(contractVO.getCreator());
        try {

            validator.validateContractReceivePaperPermissions(user.getRole());
            return Responses.success(contractService.receivePaper(id, contractVO, user));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }
}