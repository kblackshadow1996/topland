package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.QuotationConverter;
import cn.topland.entity.User;
import cn.topland.service.QuotationService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.StringReader;
import cn.topland.vo.QuotationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/quotation")
public class QuotationController {

    @Autowired
    private StringReader stringReader;

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private QuotationConverter quotationConverter;

    @GetMapping(value = "/pdf/download")
    public Response downloadPdf(String html, String title, String identity, LocalDate date) {

        try {

            return Responses.success(quotationService.downloadPdf(readHtml(html), title, identity, date));
        } catch (IOException e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    public Response add(@RequestBody QuotationVO quotationVO) {

        User user = userService.get(quotationVO.getCreator());
        try {

            validator.validateQuotationCreatePermission(user.getRole());
            return Responses.success(quotationConverter.toDTO(quotationService.add(quotationVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody QuotationVO quotationVO) {

        User user = userService.get(quotationVO.getCreator());
        try {

            validator.validateQuotationUpdatePermission(user.getRole());
            return Responses.success(quotationConverter.toDTO(quotationService.update(id, quotationVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String readHtml(String html) {

        return html.startsWith("http")
                ? stringReader.read(html)
                : html;
    }
}