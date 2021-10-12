package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.BrandConverter;
import cn.topland.entity.User;
import cn.topland.service.BrandService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.UniqueException;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private BrandConverter brandConverter;

    @PostMapping("/add")
    public Response add(@RequestBody BrandVO brandVO, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateBrandCreatePermissions(user.getRole());
            return Responses.success(brandConverter.toDTO(brandService.add(brandVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }

    @PatchMapping("/update")
    public Response update(@RequestBody BrandVO brandVO, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateBrandUpdatePermissions(user.getRole());
            return Responses.success(brandConverter.toDTO(brandService.update(brandVO, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }
}