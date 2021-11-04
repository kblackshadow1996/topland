package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.UserDTO;
import cn.topland.dto.converter.UserConverter;
import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 */
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
     */
    @PostMapping("/wework/sync/all")
    public Response<List<UserDTO>> syncAll(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                           Long creator, @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
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
     */
    @PostMapping(value = "/wework/sync")
    public Response<List<UserDTO>> sync(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                        String deptId, Long creator,
                                        @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
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
     */
    @PatchMapping(value = "/auth/{id}")
    public Response<UserDTO> auth(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                  @PathVariable Long id, @RequestBody UserVO userVO,
                                  @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
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
     */
    @PatchMapping(value = "/auth")
    public Response<List<UserDTO>> auth(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                        @RequestBody UserVO userVO,
                                        @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(userVO.getCreator());
        validator.validateUserAuthPermissions(user, token);
        return Responses.success(userConverter.toDTOs(userService.auth(userVO)));
    }
}