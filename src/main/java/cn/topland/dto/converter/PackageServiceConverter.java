package cn.topland.dto.converter;

import cn.topland.dto.PackageServiceDTO;
import cn.topland.entity.PackageService;
import cn.topland.entity.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackageServiceConverter extends BaseConverter<PackageService, PackageServiceDTO> {

    @Override
    public List<PackageServiceDTO> toDTOs(List<PackageService> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PackageServiceDTO toDTO(PackageService service) {

        return service != null
                ? composePackageServiceDTO(service)
                : null;
    }

    private PackageServiceDTO composePackageServiceDTO(PackageService service) {

        PackageServiceDTO dto = new PackageServiceDTO();
        dto.setService(getService(service.getService()));
        dto.setId(service.getId());
        dto.setUnit(service.getUnit());
        dto.setPrice(service.getPrice());
        dto.setDelivery(service.getDelivery());
        return dto;
    }

    private Long getService(Service service) {

        return service != null
                ? service.getId()
                : null;
    }
}