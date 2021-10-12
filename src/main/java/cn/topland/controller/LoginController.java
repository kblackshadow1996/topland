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

    @GetMapping("/wework/config")
    public Response weworkConfig() {

        try {

            return Responses.success(weworkConfig);
        } catch (Exception e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping(value = "/wework/login")
    public Response loginByWework(String code, String state) {

        try {

            if (StringUtils.isNotBlank(code) && StringUtils.equals(state, weworkConfig.getState())) {

                return Responses.success(converter.toDTO(userService.loginByWework(code)));
            }
            return Responses.fail(Response.INTERNAL_SERVER_ERROR, "扫描二维码失败");
        } catch (Exception e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }
}