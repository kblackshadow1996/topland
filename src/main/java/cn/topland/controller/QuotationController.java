package cn.topland.controller;

import cn.topland.service.QuotationService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.StringReader;
import cn.topland.vo.QuotationPdfVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/quotation")
public class QuotationController {

    @Autowired
    private StringReader stringReader;

    @Autowired
    private QuotationService service;

    @GetMapping(value = "/pdf/download", consumes = "application/json")
    public Response downloadPdf(@RequestBody QuotationPdfVO pdf) {

        try {

            return Responses.success(service.downloadPdf(readHtml(pdf.getHtml()), pdf.getTitle(), pdf.getIdentity(), pdf.getDate()));
        } catch (IOException e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String readHtml(String html) {

        return html.startsWith("http")
                ? stringReader.read(html)
                : html;
    }
}