package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class QuotationPdfVO implements Serializable {

    /**
     * 报价html
     */
    private String html;

    /**
     * 标题
     */
    private String title;

    /**
     * 编号
     */
    private String identity;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * 创建人ID
     */
    private Long creator;
}