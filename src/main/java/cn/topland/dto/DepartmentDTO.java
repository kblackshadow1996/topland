package cn.topland.dto;

import cn.topland.entity.Department;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class DepartmentDTO implements Serializable {

    private Long id;

    private String name;

    @JsonProperty(value = "dept_id")
    private String deptId;

    private Long parent;

    private Long sort;

    private Department.Type type;

    private Department.Source source;

    private Long creator;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(value = "create_time")
    private LocalDateTime createTime;
}