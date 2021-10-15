package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.ExceptionConverter;
import cn.topland.entity.Attachment;
import cn.topland.entity.User;
import cn.topland.service.AttachmentService;
import cn.topland.service.ExceptionService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
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

    @PostMapping("/add")
    public Response add(@RequestBody List<ExceptionVO> exceptionVOs) throws AccessException {

        User user = userService.get(exceptionVOs.get(0).getCreator());
        validator.validateExceptionCreatePermissions(user.getRole());
        List<Attachment> attachments = uploadAttachments(exceptionVOs);
        return Responses.success(exceptionConverter.toDTOs(
                exceptionService.add(exceptionVOs, attachments, user)
        ));
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody ExceptionVO exceptionVO) throws AccessException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionUpdatePermissions(user.getRole());
        List<Attachment> attachments = uploadAttachments(List.of(exceptionVO));
        return Responses.success(exceptionConverter.toDTO(
                exceptionService.update(id, exceptionVO, attachments, user)
        ));
    }

    @PatchMapping("/solve/{id}")
    public Response solve(@PathVariable Long id, @RequestBody ExceptionVO exceptionVO) throws AccessException {

        User user = userService.get(exceptionVO.getCreator());
        validator.validateExceptionSolvePermissions(user.getRole());
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