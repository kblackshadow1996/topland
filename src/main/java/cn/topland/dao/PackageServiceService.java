package cn.topland.dao;

import cn.topland.entity.IdEntity;
import cn.topland.entity.PackageService;
import cn.topland.vo.PackageServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PackageServiceService {

    @Autowired
    private PackageServiceRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    public List<PackageService> add(List<PackageServiceVO> serviceVOs) {

        return repository.saveAllAndFlush(createServices(serviceVOs));
    }

    @Transactional
    public List<PackageService> update(List<PackageService> services, List<PackageServiceVO> serviceVOs) {

        return repository.saveAllAndFlush(updateServices(services, serviceVOs));
    }

    private List<PackageService> updateServices(List<PackageService> services, List<PackageServiceVO> serviceVOs) {

        Map<Long, cn.topland.entity.Service> serviceMap = getServices(serviceVOs);
        Map<Long, PackageService> packageServiceMap = services.stream().collect(Collectors.toMap(IdEntity::getId, p -> p));
        return serviceVOs.stream().map(serviceVO -> {

            return packageServiceMap.containsKey(serviceVO.getId())
                    ? updateService(packageServiceMap.get(serviceVO.getId()), serviceVO, serviceMap.get(serviceVO.getService()))
                    : createService(serviceVO, serviceMap.get(serviceVO.getService()));
        }).collect(Collectors.toList());
    }

    private List<PackageService> createServices(List<PackageServiceVO> serviceVOs) {

        Map<Long, cn.topland.entity.Service> serviceMap = getServices(serviceVOs);
        return serviceVOs.stream()
                .map(serviceVO -> createService(serviceVO, serviceMap.get(serviceVO.getService())))
                .collect(Collectors.toList());
    }

    private Map<Long, cn.topland.entity.Service> getServices(List<PackageServiceVO> serviceVOs) {

        List<Long> serviceIds = serviceVOs.stream().map(PackageServiceVO::getService).collect(Collectors.toList());
        return serviceRepository.findAllById(serviceIds).stream().
                collect(Collectors.toMap(IdEntity::getId, s -> s));
    }

    private PackageService createService(PackageServiceVO serviceVO, cn.topland.entity.Service service) {

        return composePackageService(serviceVO, service, new PackageService());
    }

    private PackageService updateService(PackageService packageService, PackageServiceVO serviceVO, cn.topland.entity.Service service) {

        packageService.setService(service);
        packageService.setUnit(serviceVO.getUnit());
        packageService.setDelivery(serviceVO.getDelivery());
        packageService.setPrice(serviceVO.getPrice());
        return packageService;
    }

    private PackageService composePackageService(PackageServiceVO serviceVO, cn.topland.entity.Service service, PackageService packageService) {

        packageService.setService(service);
        packageService.setPrice(serviceVO.getPrice());
        packageService.setUnit(serviceVO.getUnit());
        packageService.setDelivery(serviceVO.getDelivery());
        return packageService;
    }
}