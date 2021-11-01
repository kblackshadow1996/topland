package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.ExceptionDTO;
import cn.topland.dto.converter.ExceptionConverter;
import cn.topland.entity.User;
import cn.topland.entity.directus.ExceptionDO;
import cn.topland.service.ExceptionService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.ExceptionVO;
import cn.topland.vo.SolutionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 异常
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private UserService userService;

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
     */
    @PostMapping("/add")
    public Response<List<ExceptionDTO>> add(@RequestBody List<ExceptionVO> exceptionVOs,
                                            @RequestParam(value = "access_token", required = true) String token) {

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
     */
    @PatchMapping("/update/{id}")
    public Response<ExceptionDTO> update(@PathVariable Long id, @RequestBody ExceptionVO exceptionVO,
                                         @RequestParam(value = "access_token", required = true) String token) {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionUpdatePermissions(user, token);
        ExceptionDO exceptionDO = exceptionService.update(id, exceptionVO, user);
        return Responses.success(exceptionConverter.toDTO(exceptionDO));
    }

    /**
     * 处理异常
     *
     * @param id         异常id
     * @param solutionVO 处理异常信息
     * @param token      操作用户token
     * @return
     */
    @PatchMapping("/solve/{id}")
    public Response<ExceptionDTO> solve(@PathVariable Long id, @RequestBody SolutionVO solutionVO,
                                        @RequestParam(value = "access_token", required = true) String token) {

        User user = userService.get(solutionVO.getCreator());
        validator.validateExceptionSolvePermissions(user, token);
        return Responses.success(exceptionConverter.toDTO(exceptionService.solve(id, solutionVO, user)));
    }
}