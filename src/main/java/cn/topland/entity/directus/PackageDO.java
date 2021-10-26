package cn.topland.entity.directus;

import cn.topland.entity.Package;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PackageDO extends DirectusRecordEntity {

    private String name;

    private String remark;

    private List<Long> services;

    public static PackageDO from(Package pkg) {

        PackageDO packageDO = new PackageDO();
        packageDO.setName(pkg.getName());
        packageDO.setRemark(pkg.getRemark());
        packageDO.setCreator(pkg.getCreator().getId());
        packageDO.setEditor(pkg.getEditor().getId());
        packageDO.setCreateTime(pkg.getCreateTime());
        packageDO.setLastUpdateTime(pkg.getLastUpdateTime());
        return packageDO;
    }
}