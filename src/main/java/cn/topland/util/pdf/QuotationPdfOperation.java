package cn.topland.util.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;

/**
 * 报价pdf操作
 */
@Slf4j
@Setter
@Getter
@Builder
public class QuotationPdfOperation implements PdfOperation {

    private static final String QUOTATION_NO_PREFIX = "报价单号:";

    private static final String QUOTATION_DATE_PREFIX = "报价日期:";

    private static final String LOGO = "/usr/local/data/img/logo.png";

    private static final float LOGO_WIDTH = 58;

    private static final float LOGO_HEIGHT = 6;

    private static final float HEADER_FONT_SIZE = 5;

    private static final float FOOTER_FONT_SIZE = 5;

    private static final float TITLE_FONT_SIZE = 11;

    private static final float TITLE_MAX_WIDTH = 350;

    /**
     * 注册中文字体
     */
    static {

        PdfFontFactory.register("/usr/local/data/fonts/微软雅黑.ttf");
    }

    /**
     * 标题
     */
    private String title;

    /**
     * 报价单号
     */
    private String number;

    /**
     * 日期
     */
    private LocalDate date;


    @Override
    public void apply(String src, String dest) {

        try {

            PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
            Document doc = new Document(pdfDoc, PageSize.A4);
            Paragraph title = createFirstPageTitle();
            Paragraph headerLeft = createHeaderLeft();
            Paragraph headerRight = createHeaderRight();
            Image logo = createLogo();
            // 以下的文本、图像位置都是经验数据，与文字、图片大小有关系
            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {

                Rectangle pageSize = pdfDoc.getPage(i).getPageSize();
                if (i == 1) { // 第一页加标题

                    doc.showTextAligned(title, pageSize.getWidth() / 2, pageSize.getTop() - 35, i, TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
                }

                // 设置左右页眉
                headerLeft.setFixedPosition(90, pageSize.getTop() - 55, 300);
                headerRight.setFixedPosition(pageSize.getRight() - 140, pageSize.getTop() - 55, 50);
                doc.add(headerLeft);
                doc.add(headerRight);
                // 设置logo
                logo.setFixedPosition(90, pageSize.getTop() - 20);
                doc.add(logo);
            }
            doc.close();
        } catch (IOException e) {

            log.error("quotation pdf operate error", e);
        }
    }

    // logo图
    private Image createLogo() throws MalformedURLException {

        Image image = new Image(ImageDataFactory.create(LOGO));
        image.setHeight(LOGO_HEIGHT);
        image.setWidth(LOGO_WIDTH);
        return image;
    }

    // 页眉右侧文字
    private Paragraph createHeaderRight() throws IOException {

        return new Paragraph(QUOTATION_DATE_PREFIX + date)
                .setMaxWidth(300)
                .setFont(PdfFontFactory.createRegisteredFont("微软雅黑", PdfEncodings.IDENTITY_H, true))
                .setFontSize(HEADER_FONT_SIZE);
    }

    // 页眉左侧文字
    private Paragraph createHeaderLeft() throws IOException {

        return new Paragraph(QUOTATION_NO_PREFIX + number)
                .setMaxWidth(300)
                .setFont(PdfFontFactory.createRegisteredFont("微软雅黑", PdfEncodings.IDENTITY_H, true))
                .setFontSize(HEADER_FONT_SIZE);
    }

    // 首页标题
    private Paragraph createFirstPageTitle() throws IOException {

        return new Paragraph(title)
                .setFont(PdfFontFactory.createRegisteredFont("微软雅黑", PdfEncodings.IDENTITY_H, true))
                .setFontSize(TITLE_FONT_SIZE)
                .setBold()
                .setMaxWidth(TITLE_MAX_WIDTH);
    }
}