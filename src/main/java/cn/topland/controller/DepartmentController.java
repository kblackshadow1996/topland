package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.entity.User;
import cn.topland.service.DepartmentService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @PostMapping("/wework/sync/all")
    public Response syncAllWeworkDept(Long userId) {

        try {

            User user = userService.get(userId);
            validator.validDepartmentPermissions(user.getRole());
            return Responses.success(departmentService.syncAllWeworkDept(user));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/wework/sync/{deptId}")
    public Response syncWeworkDept(@PathVariable String deptId, Long userId) {

        try {

            User user = userService.get(userId);
            validator.validDepartmentPermissions(user.getRole());
            return Responses.success(departmentService.syncWeworkDept(deptId, userService.get(userId)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}