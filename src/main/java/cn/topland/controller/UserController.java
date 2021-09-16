package cn.topland.controller;

import cn.topland.dto.converter.UserConverter;
import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.annotation.bind.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/sync/wework/all")
    public Response syncAll(@SessionUser User creator) {

        try {

            return Responses.success(userConverter.toUsersDTO(userService.syncAllWeworkUser(creator)));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_ERROR, e.getMessage());
        }
    }

    @GetMapping("/sync/wework")
    public Response sync(String deptId, @SessionUser User creator) {

        try {

            return Responses.success(userConverter.toUsersDTO(userService.syncWeworkUser(deptId, creator)));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_ERROR, e.getMessage());
        }
    }
}