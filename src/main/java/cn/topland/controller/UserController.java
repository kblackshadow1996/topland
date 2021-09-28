package cn.topland.controller;

import cn.topland.dto.converter.UserConverter;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/wework/sync/all")
    public Response syncAll() {

        try {

            return Responses.success(userConverter.toUsersDTOs(userService.syncAllWeworkUser()));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/wework/sync", consumes = "application/json")
    public Response sync(@RequestBody DepartmentVO department) {

        try {

            return Responses.success(userConverter.toUsersDTOs(userService.syncWeworkUser(department.getDeptId())));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}