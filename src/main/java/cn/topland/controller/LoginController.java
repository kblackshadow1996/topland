package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import cn.topland.dto.converter.UserConverter;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取企业微信二维码构造参数
     *
     * @return
     */
    @GetMapping("/wework/config")
    public Response weworkConfig() {

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
    public Response loginByWework(String code, String state) {

        if (StringUtils.isNotBlank(code) && StringUtils.equals(state, weworkConfig.getState())) {

            return Responses.success(converter.toDTO(userService.loginByWework(code)));
        }
        return Responses.fail(Response.EXTERNAL_SERVICE_UNAVAILABLE, "扫描二维码失败");

    }
}