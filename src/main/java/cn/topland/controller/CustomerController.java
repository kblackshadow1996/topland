package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.CustomerConverter;
import cn.topland.entity.User;
import cn.topland.entity.directus.CustomerDO;
import cn.topland.service.ContactService;
import cn.topland.service.CustomerService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private CustomerConverter customerConverter;

    /**
     * 增加客户
     *
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     */
    @PostMapping("/add")
    public Response add(@RequestBody CustomerVO customerVO, String token) {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerCreatePermissions(user, token);
        CustomerDO customerDO = customerService.add(customerVO, user);
        return Responses.success(customerConverter.toDTO(customerDO));
    }

    /**
     * 更新客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token) {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerUpdatePermissions(user, token);
        CustomerDO customerDO = customerService.update(id, customerVO, user);
        return Responses.success(customerConverter.toDTO(customerDO));
    }

    /**
     * 流失客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     */
    @PatchMapping("/lost/{id}")
    public Response lost(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token) {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerLostPermissions(user, token);
        CustomerDO customerDO = customerService.lost(id, customerVO, user);
        return Responses.success(customerConverter.toDTO(customerDO));
    }

    /**
     * 寻回客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     */
    @PatchMapping("/retrieve/{id}")
    public Response retrieve(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token) {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerRetrievePermissions(user, token);
        CustomerDO customerDO = customerService.retrieve(id, user);
        return Responses.success(customerConverter.toDTO(customerDO));
    }
}