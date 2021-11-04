package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.ContractDTO;
import cn.topland.dto.converter.ContractConverter;
import cn.topland.entity.User;
import cn.topland.entity.directus.ContractDO;
import cn.topland.service.ContractService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.ContractReviewVO;
import cn.topland.vo.ContractVO;
import cn.topland.vo.PaperVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 合同
 */
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

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
     */
    @PostMapping("/add")
    public Response<ContractDTO> add(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                     @RequestBody ContractVO contractVO,
                                     @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(contractVO.getCreator());
        validator.validateContractCreatePermissions(user, token);
        return Responses.success(contractConverter.toDTO(contractService.add(contractVO, user)));
    }

    /**
     * 收到纸质合同
     *
     * @param id      合同id
     * @param paperVO 合同信息
     * @param token   操作用户token
     * @return
     */
    @PatchMapping("/receive-paper/{id}")
    public Response<ContractDTO> receivePaper(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                              @PathVariable Long id, @RequestBody PaperVO paperVO,
                                              @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(paperVO.getCreator());
        validator.validateContractReceivePaperPermissions(user, token);
        ContractDO contractDO = contractService.receivePaper(id, paperVO, user);
        return Responses.success(contractConverter.toDTO(contractDO));
    }

    /**
     * 审核合同
     *
     * @param id       合同id
     * @param reviewVO 合同信息
     * @param token    操作用户token
     * @return
     */
    @PatchMapping("/review/{id}")
    public Response<ContractDTO> review(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                        @PathVariable Long id, @RequestBody ContractReviewVO reviewVO,
                                        @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(reviewVO.getCreator());
        validator.validateContractReviewPermissions(user, token);
        ContractDO contractDO = contractService.review(id, reviewVO, user);
        return Responses.success(contractConverter.toDTO(contractDO));
    }
}