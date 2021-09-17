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

import javax.servlet.http.HttpSession;

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

    @GetMapping("/config/wework")
    public Response weworkConfig() {

        try {

            return Responses.success(weworkConfig);
        } catch (Exception e) {

            return Responses.fail(Response.ACCESS_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * @param code    企业微信登录成功回调时用户特定字符，有效期5分钟
     * @param session 会话
     */
    @GetMapping("/login/wework")
    public Response loginByWework(String code, String state, HttpSession session) {

        try {

            if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state) && StringUtils.equals(state, weworkConfig.getState())) {

                return Responses.success(converter.toUserDTO(userService.loginByWework(code, session)));
            }
            return Responses.fail(Response.INTERNAL_ERROR, "scan wework qr code failed");
        } catch (Exception e) {

            return Responses.fail(Response.ACCESS_FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping("/logout")
    public Response logout(HttpSession session) {

        try {

            userService.logout(session);
            return Responses.success();
        } catch (Exception e) {

            return Responses.fail(Response.ACCESS_FORBIDDEN, e.getMessage());
        }
    }
}