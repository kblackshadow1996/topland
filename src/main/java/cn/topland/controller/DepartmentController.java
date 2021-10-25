package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.DepartmentConverter;
import cn.topland.entity.User;
import cn.topland.service.DepartmentService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
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

    /**
     * 同步所有部门
     *
     * @param creator 操作用户
     * @param token   操作用户token
     * @return
     * @throws AccessException
     */
    @PostMapping("/wework/sync/all")
    public Response syncAllWeworkDept(Long creator, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(creator);
        validator.validDepartmentPermissions(user, token);
        return Responses.success(departmentConverter.toDTOs(departmentService.syncAllWeworkDept(user)));
    }

    /**
     * 同步单个部门
     *
     * @param deptId  部门id(企业微信)
     * @param creator 操作用户
     * @param token   操作用户token
     * @return
     * @throws AccessException
     * @throws InvalidException
     * @throws QueryException
     */
    @PostMapping("/wework/sync/{deptId}")
    public Response syncWeworkDept(@PathVariable String deptId, Long creator, String token)
            throws AccessException, InvalidException, QueryException, InternalException {

        User user = userService.get(creator);
        validator.validDepartmentPermissions(user, token);
        return Responses.success(departmentConverter.toDTOs(departmentService.syncWeworkDept(deptId, user)));
    }
}