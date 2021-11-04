package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.BrandDTO;
import cn.topland.dto.converter.BrandConverter;
import cn.topland.entity.User;
import cn.topland.entity.directus.BrandDO;
import cn.topland.service.BrandService;
import cn.topland.service.ContactService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.BrandVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private BrandConverter brandConverter;

    /**
     * 添加品牌
     *
     * @param brandVO 品牌信息
     * @param token   操作用户token
     * @return
     */
    @PostMapping("/add")
    public Response<BrandDTO> add(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                  @RequestBody BrandVO brandVO,
                                  @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(brandVO.getCreator());
        validator.validateBrandCreatePermissions(user, token);
        BrandDO brandDO = brandService.add(brandVO, user);
        return Responses.success(brandConverter.toDTO(brandDO));
    }

    /**
     * 更新品牌
     *
     * @param id      品牌id
     * @param brandVO 品牌信息
     * @param token   操作用户token
     * @return
     */
    @PatchMapping("/update/{id}")
    public Response<BrandDTO> update(@RequestHeader(value = "topak-v1", required = false) String topToken,
                                     @PathVariable Long id, @RequestBody BrandVO brandVO,
                                     @RequestParam(value = "access_token", required = false) String token) {

        token = StringUtils.isNotBlank(topToken) ? topToken : token;
        User user = userService.get(brandVO.getCreator());
        validator.validateBrandUpdatePermissions(user, token);
        BrandDO brandDO = brandService.update(id, brandVO, user);
        return Responses.success(brandConverter.toDTO(brandDO));
    }
}