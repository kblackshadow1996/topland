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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    /**
     * pdf下载
     *
     * @param html     合同页面
     * @param title    合同标题
     * @param identity 合同编号
     * @param date     合同日期
     * @param creator  操作用户
     * @param token    操作用户token
     * @return
     */
    @GetMapping(value = "/pdf")
    public Response downloadPdf(String html, String title, String identity,
                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                Long creator, @RequestParam(value = "access_token") String token) {

        User user = userService.get(creator);
        validator.validateQuotationCreatePermission(user, token);
        return Responses.success(quotationService.downloadPdf(readHtml(html), title, identity, date));
    }

    /**
     * 新增报价合同
     *
     * @param quotationVO 报价信息
     * @param token       操作用户token
     * @return
     */
    @PostMapping("/add")
    public Response add(@RequestBody QuotationVO quotationVO, @RequestParam(value = "access_token") String token) {

        User user = userService.get(quotationVO.getCreator());
        validator.validateQuotationCreatePermission(user, token);
        return Responses.success(quotationConverter.toDTO(quotationService.add(quotationVO, user)));
    }

    /**
     * 更新报价合同
     *
     * @param id          报价id
     * @param quotationVO 报价信息
     * @param token       操作用户token
     * @return
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody QuotationVO quotationVO, @RequestParam(value = "access_token") String token) {

        User user = userService.get(quotationVO.getCreator());
        validator.validateQuotationUpdatePermission(user, token);
        return Responses.success(quotationConverter.toDTO(quotationService.update(id, quotationVO, user)));
    }

    private String readHtml(String html) {

        return html.startsWith("http")
                ? stringReader.read(html)
                : html;
    }
}