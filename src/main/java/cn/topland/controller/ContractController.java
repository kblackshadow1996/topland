package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.ContractConverter;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.ContractService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
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

    /**
     * 新增合同
     *
     * @param contractVO 合同信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PostMapping("/add")
    public Response add(@RequestBody ContractVO contractVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(contractVO.getCreator());
        validator.validateContractCreatePermissions(user, token);
        return Responses.success(contractConverter.toDTO(contractService.add(contractVO, user)));
    }

    /**
     * 收到纸质合同
     *
     * @param id         合同id
     * @param contractVO 合同信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PatchMapping("/receive-paper/{id}")
    public Response receivePaper(@PathVariable Long id, @RequestBody ContractVO contractVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(contractVO.getCreator());
        validator.validateContractReceivePaperPermissions(user, token);
        return Responses.success(contractConverter.toDTO(
                contractService.receivePaper(id, contractVO, attachmentService.upload(contractVO.getAttachments()), user)
        ));
    }

    /**
     * 审核合同
     *
     * @param id         合同id
     * @param contractVO 合同信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PatchMapping("/review/{id}")
    public Response review(@PathVariable Long id, @RequestBody ContractVO contractVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(contractVO.getCreator());
        validator.validateContractReviewPermissions(user, token);
        return Responses.success(contractConverter.toDTO(contractService.review(id, contractVO, user)));
    }
}