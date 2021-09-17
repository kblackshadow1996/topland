package cn.topland;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleTester {

    public static final String DEST = "/home/zhuliangbin/桌面/2pdf";
    public static final String SRC = "/home/zhuliangbin/桌面/1.pdf";
    public static final String LOGO = "./src/main/resources/img/logo.png";

    public static void main(String[] args) {

        HtmlToPdfParams params = new HtmlToPdfParamsFactory().quotation();
        new HtmlToPdfOperation(params).apply("/home/zhuliangbin/桌面/test.html", SRC);

        PdfOperation pdfOperation = QuotationPdfOperation
                .builder()
                .title("图澜文化&合作公司 服务报价表")
                .date(LocalDate.now())
                .number("123456")
                .build();
        pdfOperation.apply(SRC, DEST);
    }
}