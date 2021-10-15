package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.BrandConverter;
import cn.topland.entity.Brand;
import cn.topland.entity.Contact;
import cn.topland.entity.User;
import cn.topland.service.BrandService;
import cn.topland.service.ContactService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add")
    public Response add(@RequestBody BrandVO brandVO) throws AccessException {

        User user = userService.get(brandVO.getCreator());
        validator.validateBrandCreatePermissions(user.getRole());
        return Responses.success(brandConverter.toDTO(
                brandService.add(brandVO, contactService.createContacts(brandVO.getContacts()), user))
        );
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody BrandVO brandVO) throws AccessException {

        User user = userService.get(brandVO.getCreator());
        validator.validateBrandUpdatePermissions(user.getRole());
        Brand brand = brandService.get(id);
        List<Contact> contacts = contactService.updateContacts(brand.getContacts(), brandVO.getContacts());
        return Responses.success(brandConverter.toDTO(brandService.update(brand, brandVO, contacts, user)));
    }
}