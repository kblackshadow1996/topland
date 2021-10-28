package cn.topland.service;

import cn.topland.dao.PackageRepository;
import cn.topland.dao.ServiceRepository;
import cn.topland.dao.gateway.PackageGateway;
import cn.topland.dao.gateway.PackageServiceGateway;
import cn.topland.entity.IdEntity;
import cn.topland.entity.Package;
import cn.topland.entity.User;
import cn.topland.entity.directus.PackageDO;
import cn.topland.entity.directus.PackageServiceDO;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.PackageServiceVO;
import cn.topland.vo.PackageVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PackageService {

    @Autowired
    private PackageRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PackageGateway packageGateway;

    @Autowired
    private PackageServiceGateway serviceGateway;

    public Package get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("套餐[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public PackageDO add(PackageVO packageVO, User creator) {

        PackageDO packageDO = packageGateway.save(createPackage(packageVO, creator), creator.getAccessToken());
        packageDO.setServices(listServices(updateServices(List.of(), packageVO.getServices(), packageDO.getId(), creator.getAccessToken())));
        return packageDO;
    }

    public PackageDO update(Long id, PackageVO packageVO, User editor) {

        Package pkg = get(id);
        PackageDO packageDO = packageGateway.update(updatePackage(pkg, packageVO, editor), editor.getAccessToken());
        packageDO.setServices(listServices(updateServices(pkg.getServices(), packageVO.getServices(), id, editor.getAccessToken())));
        return packageDO;
    }

    private List<Long> listServices(List<PackageServiceDO> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().filter(service -> service.getPkgId() != null)
                .map(PackageServiceDO::getId).collect(Collectors.toList());
    }

    private List<PackageServiceDO> updateServices(List<cn.topland.entity.PackageService> services, List<PackageServiceVO> serviceVOs, Long pkg, String token) {

        Map<Long, cn.topland.entity.Service> serviceMap = getServices(serviceVOs);
        Map<Long, cn.topland.entity.PackageService> packageServiceMap = services.stream().collect(Collectors.toMap(IdEntity::getId, p -> p));
        List<cn.topland.entity.PackageService> packageServices = new ArrayList<>();
        List<cn.topland.entity.PackageService> updates = new ArrayList<>();
        serviceVOs.forEach(serviceVO -> {

            if (packageServiceMap.containsKey(serviceVO.getId())) {

                cn.topland.entity.PackageService service = packageServiceMap.get(serviceVO.getId());
                packageServices.add(composeService(pkg, service, serviceVO, serviceMap.get(serviceVO.getService())));
                updates.add(service);
            } else {

                packageServices.add(createService(pkg, serviceVO, serviceMap.get(serviceVO.getService())));
            }
        });
        List<cn.topland.entity.PackageService> deletes = (List<cn.topland.entity.PackageService>) CollectionUtils.removeAll(services, updates);
        deletes.forEach(delete -> {

            delete.setPkgId(null);
        });
        packageServices.addAll(deletes);
        return serviceGateway.saveAll(packageServices, token);
    }

    private Map<Long, cn.topland.entity.Service> getServices(List<PackageServiceVO> serviceVOs) {

        List<Long> serviceIds = serviceVOs.stream().map(PackageServiceVO::getService).collect(Collectors.toList());
        return serviceRepository.findAllById(serviceIds).stream().
                collect(Collectors.toMap(IdEntity::getId, s -> s));
    }

    private cn.topland.entity.PackageService createService(Long pkgId, PackageServiceVO serviceVO, cn.topland.entity.Service service) {

        return composeService(pkgId, new cn.topland.entity.PackageService(), serviceVO, service);
    }

    private cn.topland.entity.PackageService composeService(Long pkgId, cn.topland.entity.PackageService packageService,
                                                            PackageServiceVO serviceVO,
                                                            cn.topland.entity.Service service) {

        packageService.setPkgId(pkgId);
        packageService.setService(service);
        packageService.setUnit(serviceVO.getUnit());
        packageService.setDelivery(serviceVO.getDelivery());
        packageService.setPrice(serviceVO.getPrice());
        return packageService;
    }

    private Package createPackage(PackageVO packageVO, User creator) {

        Package pkg = new Package();
        composePackage(pkg, packageVO);
        pkg.setCreator(creator);
        pkg.setEditor(creator);
        return pkg;
    }

    private Package updatePackage(Package pkg, PackageVO packageVO, User editor) {

        composePackage(pkg, packageVO);
        pkg.setEditor(editor);
        pkg.setLastUpdateTime(LocalDateTime.now());
        return pkg;
    }

    private void composePackage(Package pkg, PackageVO packageVO) {

        pkg.setName(packageVO.getName());
        pkg.setRemark(packageVO.getRemark());
    }
}