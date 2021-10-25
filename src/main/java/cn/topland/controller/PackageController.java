package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.PackageConverter;
import cn.topland.entity.Package;
import cn.topland.entity.User;
import cn.topland.entity.directus.DirectusIdEntity;
import cn.topland.entity.directus.PackageDO;
import cn.topland.entity.directus.PackageServiceDO;
import cn.topland.service.PackageService;
import cn.topland.service.PackageServiceService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.PackageVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 新增服务套餐
     *
     * @param packageVO 套餐信息
     * @param token     操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PostMapping("/add")
    public Response add(@RequestBody PackageVO packageVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageCreatePermissions(user, token);
        PackageDO packageDO = packageService.add(packageVO, user);
        packageDO.setServices(listServices(packageServiceService.add(packageDO.getId(), packageVO.getServices(), token)));
        return Responses.success(packageConverter.toDTO(packageDO));
    }

    /**
     * 更新产品套餐
     *
     * @param id        套餐id
     * @param packageVO 套餐信息
     * @param token     操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody PackageVO packageVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageUpdatePermissions(user, token);
        Package pkg = packageService.get(id);
        PackageDO packageDO = packageService.update(pkg, packageVO, user);
        packageDO.setServices(listServices(packageServiceService.update(pkg.getId(), pkg.getServices(), packageVO.getServices(), token)));
        return Responses.success(packageConverter.toDTO(packageDO));
    }

    private List<Long> listServices(List<PackageServiceDO> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().map(DirectusIdEntity::getId).collect(Collectors.toList());
    }
}