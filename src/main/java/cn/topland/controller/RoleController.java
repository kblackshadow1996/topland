package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.RoleConverter;
import cn.topland.entity.User;
import cn.topland.service.RoleService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private RoleConverter roleConverter;

    @PostMapping("/add")
    public Response add(@RequestBody RoleVO roleVO) throws AccessException {

        User user = userService.get(roleVO.getCreator());
        validator.validateRoleCreatePermissions(user.getRole());
        return Responses.success(roleConverter.toDTO(roleService.add(roleVO, user)));
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody RoleVO roleVO) throws AccessException {

        User user = userService.get(roleVO.getCreator());
        validator.validateRoleUpdatePermissions(user.getRole());
        return Responses.success(roleConverter.toDTO(roleService.update(id, roleVO, user)));
    }
}