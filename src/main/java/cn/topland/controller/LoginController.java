package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.UserDTO;
import cn.topland.dto.converter.UserConverter;
import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 */
@RestController
public class LoginController {

    @Autowired
    private WeworkConfig weworkConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter converter;

    @Autowired
    private PermissionValidator validator;

    /**
     * 获取企业微信二维码构造参数
     *
     * @return
     */
    @GetMapping("/wework/config")
    public Response<WeworkConfig> weworkConfig() {

        return Responses.success(weworkConfig);
    }

    /**
     * 登录
     *
     * @param code  企业微信扫码返回code
     * @param state 系统随机state
     * @return
     */
    @GetMapping(value = "/wework/login")
    public Response<UserDTO> loginByWework(String code, String state) {

        if (StringUtils.isNotBlank(code) && StringUtils.equals(state, weworkConfig.getState())) {

            return Responses.success(converter.toDTO(userService.loginByWework(code)));
        }
        return Responses.fail(Response.EXTERNAL_SERVICE_UNAVAILABLE, "扫描二维码失败");
    }

    /**
     * 刷新口令
     *
     * @param userId 用户id
     * @return
     */
    @PatchMapping(value = "/refresh-token/{userId}")
    public Response<String> refreshToken(@PathVariable Long userId) {

        User user = userService.get(userId);
        validator.validateUserRefreshPermissions(user, user.getAccessToken());
        return Responses.success(userService.refreshToken(userId));
    }

    /**
     * 登出
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping(value = "/logout/{userId}")
    public Response<String> logout(@PathVariable Long userId) {

        User user = userService.get(userId);
        validator.validateUserAuthPermissions(user, user.getAccessToken());
        userService.logout(userId);
        return Responses.success("登出成功");
    }
}