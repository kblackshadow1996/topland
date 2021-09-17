package cn.topland.controller;

import cn.topland.service.QuotationService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/quotation")
public class QuotationController {

    @Autowired
    private QuotationService service;

    /**
     * @param title  标题名称
     * @param number 报价单号
     * @param date   报价日期
     * @return
     */
    @PostMapping("/pdf/download")
    public Response downloadPdf(String html, String title, String number, LocalDate date) {

        try {

            return Responses.success(service.downloadPdf(html, title, number, date));
        } catch (IOException e) {

            return Responses.fail(Response.INTERNAL_ERROR, "download pdf error: " + e.getMessage());
        }
    }
}