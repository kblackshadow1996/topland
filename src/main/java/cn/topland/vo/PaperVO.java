package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class PaperVO implements Serializable {

    /**
     * 纸质合同日期：yyyy-MM-dd
     */
    @JsonProperty(value = "paper_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paperDate;

    /**
     * 附件ID
     */
    private String attachments;

    /**
     * 操作人ID
     */
    private Long creator;
}