package cn.topland.dto.converter;

import cn.topland.dto.PackageDTO;
import cn.topland.entity.Package;
import cn.topland.entity.PackageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackageConverter extends BaseConverter<Package, PackageDTO> {

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
        dto.setServices(listServiceIds(pkg.getServices()));
        dto.setCreator(getId(pkg.getCreator()));
        dto.setEditor(getId(pkg.getEditor()));
        dto.setCreateTime(pkg.getCreateTime());
        dto.setLastUpdateTime(pkg.getLastUpdateTime());
        return dto;
    }

    private List<Long> listServiceIds(List<PackageService> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().map(this::getId).collect(Collectors.toList());
    }
}