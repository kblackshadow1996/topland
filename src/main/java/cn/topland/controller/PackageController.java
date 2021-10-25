package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.PackageConverter;
import cn.topland.entity.Package;
import cn.topland.entity.User;
import cn.topland.service.PackageService;
import cn.topland.service.PackageServiceService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
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
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageCreatePermissions(user, token);
        List<cn.topland.entity.PackageService> services = packageServiceService.add(packageVO.getServices());
        return Responses.success(packageConverter.toDTO(packageService.add(packageVO, services, user)));
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
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(packageVO.getCreator());
        validator.validatePackageUpdatePermissions(user, token);
        Package pkg = packageService.get(id);
        List<cn.topland.entity.PackageService> services = packageServiceService.update(pkg.getServices(), packageVO.getServices());
        return Responses.success(packageConverter.toDTO(packageService.update(pkg, packageVO, services, user)));
    }
}