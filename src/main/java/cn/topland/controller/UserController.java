package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.UserConverter;
import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PermissionValidator validator;

    @PostMapping("/wework/sync/all")
    public Response syncAll(Long userId) {

        try {

            User user = userService.get(userId);
            validator.validateUserPermissions(user.getRole());
            return Responses.success(userConverter.toDTOs(userService.syncAllWeworkUser(user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(value = "/wework/sync")
    public Response sync(String deptId, Long userId) {

        try {

            User user = userService.get(userId);
            validator.validateUserPermissions(user.getRole());
            return Responses.success(userConverter.toDTOs(userService.syncWeworkUser(deptId, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}