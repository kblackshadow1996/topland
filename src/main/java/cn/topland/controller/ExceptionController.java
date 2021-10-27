package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.ExceptionConverter;
import cn.topland.entity.Exception;
import cn.topland.entity.User;
import cn.topland.entity.directus.ExceptionDO;
import cn.topland.service.AttachmentService;
import cn.topland.service.ExceptionService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.ExceptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private ExceptionConverter exceptionConverter;

    /**
     * 新增异常
     *
     * @param exceptionVOs 异常信息
     * @param token        操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PostMapping("/add")
    public Response add(@RequestBody List<ExceptionVO> exceptionVOs, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(exceptionVOs.get(0).getCreator());
        validator.validateExceptionCreatePermissions(user, token);
        List<ExceptionDO> exceptionDOs = exceptionService.add(exceptionVOs, user);
        return Responses.success(exceptionConverter.toDTOs(exceptionDOs));
    }

    /**
     * 更新异常
     *
     * @param id          异常id
     * @param exceptionVO 异常信息
     * @param token       操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody ExceptionVO exceptionVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionUpdatePermissions(user, token);
        ExceptionDO exceptionDO = exceptionService.update(id, exceptionVO, user);
        return Responses.success(exceptionConverter.toDTO(exceptionDO));
    }

    /**
     * 处理异常
     *
     * @param id          异常id
     * @param exceptionVO 处理异常信息
     * @param token       操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PatchMapping("/solve/{id}")
    public Response solve(@PathVariable Long id, @RequestBody ExceptionVO exceptionVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionSolvePermissions(user, token);
        return Responses.success(exceptionConverter.toDTO(exceptionService.solve(id, exceptionVO, user)));
    }
}