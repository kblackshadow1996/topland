package cn.topland.util.pdf;

import static cn.topland.util.pdf.HtmlToPdfParams.PageSize;

/**
 * pdf生成参数工厂
 */
public class HtmlToPdfParamsFactory {

    public HtmlToPdfParams quotation() {

        return HtmlToPdfParams.builder()
                .pageSize(PageSize.A4)
                .margin(new PageMargin(25.4, 25.4, 31.8, 31.8))
                .headerSpacing(5)
                .headerLine(true)
                .footerSpacing(5)
                .footerFontSize(8)
                .footerCenter("[page]")
                .build();
    }
}