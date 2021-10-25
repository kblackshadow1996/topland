package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentDO extends DirectusRecordEntity {

    private String name;

    private String deptId;

    private Long parent;

    private Long sort;

    private String type;

    private String source;
}