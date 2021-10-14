package cn.topland.dto.converter;

import cn.topland.dto.PackageDTO;
import cn.topland.entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PackageConverter extends BaseConverter<Package, PackageDTO> {

    @Autowired
    private PackageServiceConverter serviceConverter;

    @Override
    public PackageDTO toDTO(Package pkg) {

        return pkg != null
                ? composePackageDTO(pkg)
                : null;
    }

    private PackageDTO composePackageDTO(Package pkg) {

        PackageDTO dto = new PackageDTO();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setRemark(pkg.getRemark());
        dto.setServices(serviceConverter.toDTOs(pkg.getServices()));
        dto.setCreator(getId(pkg.getCreator()));
        dto.setEditor(getId(pkg.getEditor()));
        dto.setCreateTime(pkg.getCreateTime());
        dto.setLastUpdateTime(pkg.getLastUpdateTime());
        return dto;
    }
}