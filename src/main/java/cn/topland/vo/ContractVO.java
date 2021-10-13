package cn.topland.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ContractVO implements Serializable {

    @JsonProperty(value = "paper_date")
    private LocalDate paperDate;

    private Long creator;

    private List<AttachmentVO> attachments;
}