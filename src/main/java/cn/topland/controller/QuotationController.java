package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.QuotationConverter;
import cn.topland.entity.User;
import cn.topland.service.QuotationService;
import cn.topland.service.UserService;
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
    public Response add(@RequestBody QuotationVO quotationVO, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateQuotationCreatePermission(user.getRole());
            return Responses.success(quotationConverter.toDTO(quotationService.add(quotationVO, user)));
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/update")
    public Response update(@RequestBody QuotationVO quotationVO, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateQuotationUpdatePermission(user.getRole());
            return Responses.success(quotationConverter.toDTO(quotationService.update(quotationVO, user)));
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