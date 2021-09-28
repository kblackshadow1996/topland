package cn.topland.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class QuotationPdfVO implements Serializable {

    private String html;

    private String title;

    private String identity;

    private LocalDate date;
}