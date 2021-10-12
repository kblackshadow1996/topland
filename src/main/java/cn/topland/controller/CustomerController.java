package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.CustomerConverter;
import cn.topland.entity.User;
import cn.topland.service.CustomerService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.UniqueException;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionValidator validator;

    @Autowired
    private CustomerConverter customerConverter;

    @PostMapping("/add")
    public Response add(@RequestBody CustomerVO customer, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateCustomerCreatePermissions(user.getRole());
            return Responses.success(customerConverter.toDTO(customerService.add(customer, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }

    @PatchMapping("/update")
    public Response update(@RequestBody CustomerVO customer, @RequestParam Long userId) {

        User user = userService.get(userId);
        try {

            validator.validateCustomerCreatePermissions(user.getRole());
            return Responses.success(customerConverter.toDTO(customerService.update(customer, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        }
    }
}