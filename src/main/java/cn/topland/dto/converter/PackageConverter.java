package cn.topland.dto.converter;

import cn.topland.dto.PackageDTO;
import cn.topland.entity.Package;
import cn.topland.entity.PackageService;
import cn.topland.entity.directus.PackageDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackageConverter extends BaseConverter<PackageDO, PackageDTO> {

    @Override
    public PackageDTO toDTO(PackageDO pkg) {

        return pkg != null
                ? composePackageDTO(pkg)
                : null;
    }

    private PackageDTO composePackageDTO(PackageDO pkg) {

        PackageDTO dto = new PackageDTO();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setRemark(pkg.getRemark());
        dto.setServices(pkg.getServices());
        dto.setCreator(pkg.getCreator());
        dto.setEditor(pkg.getEditor());
        dto.setCreateTime(pkg.getCreateTime());
        dto.setLastUpdateTime(pkg.getLastUpdateTime());
        return dto;
    }
}