package cn.topland.util.pdf;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * html转pdf参数类
 * 注：wkhtmltox转换pdf效果较好，但是对新增样式如页眉等不太友好，所以暂时只提供部分参数设置，itext做复杂的操作
 */
@Slf4j
@Setter
@Getter
@Builder
public class HtmlToPdfParams {

    /**
     * 页面大小
     */
    private PageSize pageSize;

    /**
     * 页边距
     */
    private PageMargin margin;

    /**
     * 页眉左下内容
     */
    private String headerLeft;

    /**
     * 页眉右下内容
     */
    private String headerRight;

    /**
     * 页眉正中内容
     */
    private String headerCenter;

    /**
     * 页脚左下内容
     */
    private String footerLeft;

    /**
     * 页脚右下内容
     */
    private String footerRight;

    /**
     * 页脚正中内容
     */
    private String footerCenter;

    /**
     * 页眉到内容距离
     */
    private Integer headerSpacing;

    /**
     * 页脚到内容距离
     */
    private Integer footerSpacing;

    /**
     * 页眉线
     */
    private boolean headerLine;

    /**
     * 页脚线
     */
    private boolean footerLine;

    /**
     * 页眉字大小
     */
    private Integer headerFontSize;

    /**
     * 页脚字大小
     */
    private Integer footerFontSize;

    public enum PageSize {

        A4,
        LETTER
    }

    public String buildParams() {

        StringBuilder params = new StringBuilder();
        if (pageSize != null) {

            params.append("--page-size ").append(pageSize.name()).append(" ");
        }
        if (margin != null) {

            if (margin.getTop() != null) {

                params.append("--margin-top ").append(margin.getTop()).append("mm ");
            }
            if (margin.getBottom() != null) {

                params.append("--margin-bottom ").append(margin.getBottom()).append("mm ");
            }
            if (margin.getLeft() != null) {

                params.append("--margin-left ").append(margin.getLeft()).append("mm ");
            }
            if (margin.getRight() != null) {

                params.append("--margin-right ").append(margin.getRight()).append("mm ");
            }
        }
        if (StringUtils.isNotBlank(headerLeft)) {

            params.append("--header-left ").append(headerLeft).append(" ");
        }
        if (StringUtils.isNotBlank(headerRight)) {

            params.append("--header-right ").append(headerRight).append(" ");
        }
        if (StringUtils.isNotBlank(headerCenter)) {

            params.append("--header-center ").append(headerCenter).append(" ");
        }
        if (StringUtils.isNotBlank(footerLeft)) {

            params.append("--footer-left ").append(footerLeft).append(" ");
        }
        if (StringUtils.isNotBlank(footerRight)) {

            params.append("--footer-right ").append(footerRight).append(" ");
        }
        if (StringUtils.isNotBlank(footerCenter)) {

            params.append("--footer-center ").append(footerCenter).append(" ");
        }
        if (headerSpacing != null) {

            params.append("--header-spacing ").append(headerSpacing).append(" ");
        }
        if (footerSpacing != null) {

            params.append("--footer-spacing ").append(footerSpacing).append(" ");
        }
        if (headerFontSize != null) {

            params.append("--header-font-size ").append(headerFontSize).append(" ");
        }
        if (footerFontSize != null) {

            params.append("--footer-font-size ").append(footerFontSize).append(" ");
        }
        if (headerLine) {

            params.append("--header-line ");
        }
        if (footerLine) {

            params.append("--footer-line ");
        }
        log.info("pdf html to pdf params: {}", params);
        return params.toString();
    }
}