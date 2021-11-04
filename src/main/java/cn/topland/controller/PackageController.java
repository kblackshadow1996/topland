package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.PackageDTO;
import cn.topland.dto.converter.PackageConverter;
import cn.topland.entity.User;
import cn.topland.entity.directus.PackageDO;
import cn.topland.service.PackageService;
import cn.topland.service.PackageServiceService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.PackageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 产品套餐
 */
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
     */
    @PostMapping("/add")
    public Response<PackageDTO> add(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                    @RequestBody PackageVO packageVO,
                                    @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(packageVO.getCreator());
        validator.validatePackageCreatePermissions(user, token);
        PackageDO packageDO = packageService.add(packageVO, user);
        return Responses.success(packageConverter.toDTO(packageDO));
    }

    /**
     * 更新产品套餐
     *
     * @param id        套餐id
     * @param packageVO 套餐信息
     * @param token     操作用户token
     * @return
     */
    @PatchMapping("/update/{id}")
    public Response<PackageDTO> update(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                       @PathVariable Long id, @RequestBody PackageVO packageVO,
                                       @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(packageVO.getCreator());
        validator.validatePackageUpdatePermissions(user, token);
        PackageDO packageDO = packageService.update(id, packageVO, user);
        return Responses.success(packageConverter.toDTO(packageDO));
    }
}