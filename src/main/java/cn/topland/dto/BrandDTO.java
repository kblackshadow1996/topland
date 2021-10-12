package cn.topland.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class BrandDTO implements Serializable {

    private Long id;

    private String name;

    private Long seller;

    private Long producer;

    private String business;

    private List<ContactDTO> contracts;

    private Long creator;

    private Long editor;

    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;

    @JsonProperty(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
}