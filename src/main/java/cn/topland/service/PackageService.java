package cn.topland.service;

import cn.topland.dao.PackageRepository;
import cn.topland.entity.Package;
import cn.topland.entity.User;
import cn.topland.vo.PackageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PackageService {

    @Autowired
    private PackageRepository repository;

    public Package get(Long id) {

        return repository.getById(id);
    }

    @Transactional
    public Package add(PackageVO packageVO, List<cn.topland.entity.PackageService> services, User creator) {

        return repository.saveAndFlush(createPackage(packageVO, services, creator));
    }

    @Transactional
    public Package update(Package pkg, PackageVO packageVO, List<cn.topland.entity.PackageService> services, User editor) {

        return repository.saveAndFlush(updatePackage(pkg, packageVO, services, editor));
    }

    private Package updatePackage(Package pkg, PackageVO packageVO, List<cn.topland.entity.PackageService> services, User editor) {

        composePackage(pkg, packageVO);
        pkg.setServices(services);
        pkg.setEditor(editor);
        pkg.setLastUpdateTime(LocalDateTime.now());
        return pkg;
    }

    private Package createPackage(PackageVO packageVO, List<cn.topland.entity.PackageService> services, User creator) {

        Package pkg = new Package();
        composePackage(pkg, packageVO);
        pkg.setServices(services);
        pkg.setCreator(creator);
        pkg.setEditor(creator);
        return pkg;
    }

    private void composePackage(Package pkg, PackageVO packageVO) {

        pkg.setName(packageVO.getName());
        pkg.setRemark(packageVO.getRemark());
    }
}