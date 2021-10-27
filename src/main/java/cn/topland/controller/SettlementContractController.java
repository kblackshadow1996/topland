package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.SettlementContractConverter;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.SettlementContractService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
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

    /**
     * 新增结算合同
     *
     * @param contractVO 结算合同信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws InvalidException
     * @throws QueryException
     */
    @PostMapping("/add")
    public Response add(@RequestBody SettlementContractVO contractVO, String token)
            throws AccessException, InvalidException, QueryException, InternalException {

        User user = userService.get(contractVO.getCreator());
        validator.validateSettlementCreatePermissions(user, token);
        return Responses.success(settlementContractConverter.toDTO(contractService.add(contractVO, user)));
    }

    /**
     * 审核结算合同
     *
     * @param id         结算合同id
     * @param contractVO 结算合同信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PatchMapping("/review/{id}")
    public Response review(@PathVariable Long id, @RequestBody SettlementContractVO contractVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(contractVO.getCreator());
        validator.validateSettlementReviewPermissions(user, token);
        return Responses.success(settlementContractConverter.toDTO(contractService.review(id, contractVO, user)));
    }
}