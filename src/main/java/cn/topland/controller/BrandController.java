package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.BrandConverter;
import cn.topland.entity.Brand;
import cn.topland.entity.User;
import cn.topland.entity.directus.BrandDO;
import cn.topland.entity.directus.ContactDO;
import cn.topland.entity.directus.DirectusSimpleIdEntity;
import cn.topland.service.BrandService;
import cn.topland.service.ContactService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.BrandVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * @throws AccessException
     */
    @PostMapping("/add")
    public Response add(@RequestBody BrandVO brandVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(brandVO.getCreator());
        validator.validateBrandCreatePermissions(user, token);
        BrandDO brandDO = brandService.add(brandVO, user);
        List<ContactDO> contacts = contactService.createBrandContacts(brandVO.getContacts(), brandDO.getId(), token);
        brandDO.setContacts(listContacts(contacts));
        return Responses.success(brandConverter.toDTO(brandDO));
    }

    /**
     * 更新品牌
     *
     * @param id      品牌id
     * @param brandVO 品牌信息
     * @param token   操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     * @throws InvalidException
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody BrandVO brandVO, String token)
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(brandVO.getCreator());
        validator.validateBrandUpdatePermissions(user, token);
        Brand brand = brandService.get(id);
        BrandDO brandDO = brandService.update(brand, brandVO, user);
        List<ContactDO> contacts = contactService.updateBrandContacts(brand.getContacts(), brandVO.getContacts(), id, token);
        brandDO.setContacts(listContacts(contacts));
        return Responses.success(brandConverter.toDTO(brandDO));
    }

    private List<Long> listContacts(List<ContactDO> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().filter(contactDO -> contactDO.getBrand() != null)
                .map(DirectusSimpleIdEntity::getId)
                .collect(Collectors.toList());
    }
}