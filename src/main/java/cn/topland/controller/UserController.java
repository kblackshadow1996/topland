package cn.topland.controller;

import cn.topland.dto.converter.UserConverter;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
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
    public Response syncAll() {

        try {

            return Responses.success(userConverter.toUsersDTOs(userService.syncAllWeworkUser()));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/sync/wework")
    public Response sync(String deptId) {

        try {

            return Responses.success(userConverter.toUsersDTOs(userService.syncWeworkUser(deptId)));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}