package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.UserConverter;
import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PermissionValidator validator;

    /**
     * 同步所有用户
     *
     * @param creator 当前操作用户id
     * @param token   当前操作用户token
     * @return
     * @throws AccessException
     * @throws InternalException
     */
    @PostMapping("/wework/sync/all")
    public Response syncAll(Long creator, String token)
            throws AccessException, InternalException, InvalidException, QueryException {

        User user = userService.get(creator);
        validator.validateUserPermissions(user, token);
        return Responses.success(userConverter.toDTOs(userService.syncAllWeworkUser(user)));
    }

    /**
     * 同步某组织用户
     *
     * @param deptId  组织id
     * @param creator 当前操作用户id
     * @param token   当前操作用户token
     * @return
     * @throws AccessException
     * @throws InternalException
     */
    @PostMapping(value = "/wework/sync")
    public Response sync(String deptId, Long creator, String token)
            throws AccessException, InternalException, InvalidException, QueryException {

        User user = userService.get(creator);
        validator.validateUserPermissions(user, token);
        return Responses.success(userConverter.toDTOs(userService.syncWeworkUser(deptId, user)));
    }

    /**
     * 用户授权
     *
     * @param id     授权用户id
     * @param userVO role用户角色;auth数据权限
     * @param token  当前操作用户token
     * @return
     * @throws AccessException
     */
    @PatchMapping(value = "/auth/{id}")
    public Response auth(@PathVariable Long id, @RequestBody UserVO userVO, String token)
            throws AccessException, InvalidException, QueryException, InternalException {

        User user = userService.get(userVO.getCreator());
        validator.validateUserAuthPermissions(user, token);
        return Responses.success(userConverter.toDTO(userService.auth(id, userVO)));
    }

    /**
     * 批量用户授权
     *
     * @param userVO users用户id;role用户角色;auth数据权限
     * @param token  当前操作用户token
     * @return
     * @throws AccessException
     */
    @PatchMapping(value = "/auth")
    public Response auth(@RequestBody UserVO userVO, String token)
            throws AccessException, InvalidException, QueryException, InternalException {

        User user = userService.get(userVO.getCreator());
        validator.validateUserAuthPermissions(user, token);
        return Responses.success(userConverter.toDTOs(userService.auth(userVO)));
    }
}