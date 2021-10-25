package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.ExceptionConverter;
import cn.topland.entity.Attachment;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.ExceptionService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.AttachmentVO;
import cn.topland.vo.ExceptionVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(exceptionVOs.get(0).getCreator());
        validator.validateExceptionCreatePermissions(user, token);
        List<Attachment> attachments = uploadAttachments(exceptionVOs);
        return Responses.success(exceptionConverter.toDTOs(
                exceptionService.add(exceptionVOs, attachments, user)
        ));
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
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionUpdatePermissions(user, token);
        List<Attachment> attachments = uploadAttachments(List.of(exceptionVO));
        return Responses.success(exceptionConverter.toDTO(
                exceptionService.update(id, exceptionVO, attachments, user)
        ));
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
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionSolvePermissions(user, token);
        return Responses.success(exceptionConverter.toDTO(
                exceptionService.solve(id, exceptionVO, user)
        ));
    }

    private List<Attachment> uploadAttachments(List<ExceptionVO> exceptionVOs) {

        List<AttachmentVO> attachments = new ArrayList<>();
        exceptionVOs.forEach(exceptionVO -> {

            if (CollectionUtils.isNotEmpty(exceptionVO.getAttachments())) {

                attachments.addAll(exceptionVO.getAttachments());
            }
        });
        return CollectionUtils.isNotEmpty(attachments)
                ? attachmentService.upload(attachments)
                : List.of();
    }
}