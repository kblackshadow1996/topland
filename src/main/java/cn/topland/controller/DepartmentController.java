package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.DepartmentConverter;
import cn.topland.entity.User;
import cn.topland.service.DepartmentService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import lombok.SneakyThrows;
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

    @Autowired
    private DepartmentConverter departmentConverter;

    @SneakyThrows
    @PostMapping("/wework/sync/all")
    public Response syncAllWeworkDept(Long creator) {

        User user = userService.get(creator);
        validator.validDepartmentPermissions(user.getRole());
        return Responses.success(departmentConverter.toDTOs(departmentService.syncAllWeworkDept(user)));
    }

    @PostMapping("/wework/sync/{deptId}")
    public Response syncWeworkDept(@PathVariable String deptId, Long creator) throws AccessException {

        User user = userService.get(creator);
        validator.validDepartmentPermissions(user.getRole());
        return Responses.success(departmentConverter.toDTOs(departmentService.syncWeworkDept(deptId, user)));
    }
}