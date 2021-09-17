package cn.topland.service;

import cn.topland.util.UUIDGenerator;
import cn.topland.util.pdf.HtmlToPdfOperation;
import cn.topland.util.pdf.HtmlToPdfParams;
import cn.topland.util.pdf.HtmlToPdfParamsFactory;
import cn.topland.util.pdf.QuotationPdfOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

@Service
public class QuotationService {

    @Value("${tmp.dir}")
    private String temDir;

    public byte[] downloadPdf(String html, String title, String number, LocalDate date) throws IOException {

        if (!Files.exists(new File(temDir).toPath())) {

            new File(temDir).mkdirs();
        }
        String randomName = UUIDGenerator.generate();
        String tmp = temDir + "/" + randomName + ".tmp.pdf";
        String dest = temDir + "/" + randomName + "pdf";

        // 生成pdf
        HtmlToPdfParams params = new HtmlToPdfParamsFactory().quotation();
        new HtmlToPdfOperation(params).apply(html, tmp);

        // 处理pdf
        QuotationPdfOperation pdfOperation = getPdfOperation(title, number, date);
        pdfOperation.apply(tmp, dest);

        return FileUtils.readFileToByteArray(new File(dest));
    }

    private QuotationPdfOperation getPdfOperation(String title, String number, LocalDate date) {

        return QuotationPdfOperation.builder()
                .title(title)
                .number(number)
                .date(date)
                .build();
    }
}