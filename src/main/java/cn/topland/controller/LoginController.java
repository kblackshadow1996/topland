package cn.topland.controller;

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
    private UserService userService;

    @Autowired
    private UserDTOConverter converter;

    /**
     * @param code    企业微信登录成功回调时用户特定字符，有效期5分钟
     * @param session 会话
     */
    @GetMapping("/login")
    public Response login(String code, HttpSession session) {

        try {

            return Responses.success(converter.toUserDTO(userService.login(code, session)));
        } catch (Exception e) {

            return Responses.fail(Response.ACCESS_FORBIDDEN, e.getMessage());
        }
    }
}