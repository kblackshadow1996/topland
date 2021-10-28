package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.RoleConverter;
import cn.topland.entity.User;
import cn.topland.service.RoleService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色
 */
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

    /**
     * 新增角色
     *
     * @param roleVO 角色信息
     * @param token  操作用户token
     * @return
     */
    @PostMapping("/add")
    public Response add(@RequestBody RoleVO roleVO,
                        @RequestParam(value = "access_token", required = true) String token) {

        User user = userService.get(roleVO.getCreator());
        validator.validateRoleCreatePermissions(user, token);
        return Responses.success(roleConverter.toDTO(roleService.add(roleVO, user)));
    }

    /**
     * 更新角色
     *
     * @param id     角色id
     * @param roleVO 角色信息
     * @param token  操作用户token
     * @return
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody RoleVO roleVO,
                           @RequestParam(value = "access_token", required = true) String token) {

        User user = userService.get(roleVO.getCreator());
        validator.validateRoleUpdatePermissions(user, token);
        return Responses.success(roleConverter.toDTO(roleService.update(id, roleVO, user)));
    }
}