package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.PackageConverter;
import cn.topland.entity.Package;
import cn.topland.entity.User;
import cn.topland.service.PackageService;
import cn.topland.service.PackageServiceService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.PackageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private PackageServiceService packageServiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private PackageConverter packageConverter;

    @PostMapping("/add")
    public Response add(@RequestBody PackageVO packageVO) throws AccessException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageCreatePermissions(user.getRole());
        List<cn.topland.entity.PackageService> services = packageServiceService.add(packageVO.getServices());
        return Responses.success(packageConverter.toDTO(packageService.add(packageVO, services, user)));
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody PackageVO packageVO) throws AccessException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageUpdatePermissions(user.getRole());
        Package pkg = packageService.get(id);
        List<cn.topland.entity.PackageService> services = packageServiceService.update(pkg.getServices(), packageVO.getServices());
        return Responses.success(packageConverter.toDTO(packageService.update(pkg, packageVO, services, user)));
    }
}