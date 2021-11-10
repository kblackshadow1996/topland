package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.QuotationDTO;
import cn.topland.dto.converter.QuotationConverter;
import cn.topland.entity.User;
import cn.topland.service.QuotationService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.StringReader;
import cn.topland.vo.QuotationPdfVO;
import cn.topland.vo.QuotationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 报价
 */
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
     * @param token 操作用户token
     * @param pdfVO pdf
     * @return
     */
    @PostMapping(value = "/pdf")
    public Response<byte[]> downloadPdf(@RequestBody QuotationPdfVO pdfVO,
                                        @RequestHeader(value = "topak-v1", required = false) String topToken,
                                        @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(pdfVO.getCreator());
        validator.validateQuotationCreatePermission(user, token);
        return Responses.success(quotationService.downloadPdf(pdfVO));
    }

    /**
     * 新增报价合同
     *
     * @param quotationVO 报价信息
     * @param token       操作用户token
     * @return
     */
    @PostMapping("/add")
    public Response<QuotationDTO> add(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                      @RequestBody QuotationVO quotationVO,
                                      @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
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
    public Response<QuotationDTO> update(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                         @PathVariable Long id, @RequestBody QuotationVO quotationVO,
                                         @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
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