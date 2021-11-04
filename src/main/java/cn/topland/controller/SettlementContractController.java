package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.SettlementContractDTO;
import cn.topland.dto.converter.SettlementContractConverter;
import cn.topland.entity.User;
import cn.topland.service.SettlementContractService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.SettlementContractVO;
import cn.topland.vo.SettlementReviewVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 结算合同
 */
@RestController
@RequestMapping("/settlement-contract")
public class SettlementContractController {

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
     */
    @PostMapping("/add")
    public Response<SettlementContractDTO> add(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                               @RequestBody SettlementContractVO contractVO,
                                               @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(contractVO.getCreator());
        validator.validateSettlementCreatePermissions(user, token);
        return Responses.success(settlementContractConverter.toDTO(contractService.add(contractVO, user)));
    }

    /**
     * 审核结算合同
     *
     * @param id       结算合同id
     * @param reviewVO 结算合同信息
     * @param token    操作用户token
     * @return
     */
    @PatchMapping("/review/{id}")
    public Response<SettlementContractDTO> review(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                                  @PathVariable Long id, @RequestBody SettlementReviewVO reviewVO,
                                                  @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(reviewVO.getCreator());
        validator.validateSettlementReviewPermissions(user, token);
        return Responses.success(settlementContractConverter.toDTO(contractService.review(id, reviewVO, user)));
    }
}