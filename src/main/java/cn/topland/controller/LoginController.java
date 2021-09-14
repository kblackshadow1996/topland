package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import cn.topland.dto.converter.UserDTOConverter;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
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
    private UserDTOConverter converter;

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
    public Response loginByWework(String code, HttpSession session) {

        try {

            return Responses.success(converter.toUserDTO(userService.loginByWework(code, session)));
        } catch (Exception e) {

            return Responses.fail(Response.ACCESS_FORBIDDEN, e.getMessage());
        }
    }
}