package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.CustomerConverter;
import cn.topland.entity.Contact;
import cn.topland.entity.Customer;
import cn.topland.entity.User;
import cn.topland.service.ContactService;
import cn.topland.service.CustomerService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @throws AccessException
     * @throws QueryException
     */
    @PostMapping("/add")
    public Response add(@RequestBody CustomerVO customerVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerCreatePermissions(user, token);
        return Responses.success(customerConverter.toDTO(
                customerService.add(customerVO, contactService.createContacts(customerVO.getContacts()), user))
        );
    }

    /**
     * 更新客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerUpdatePermissions(user, token);
        Customer customer = customerService.get(id);
        List<Contact> contacts = contactService.updateContacts(customer.getContacts(), customerVO.getContacts());
        return Responses.success(customerConverter.toDTO(customerService.update(customer, customerVO, contacts, user)));
    }

    /**
     * 流失客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PatchMapping("/lost/{id}")
    public Response lost(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerLostPermissions(user, token);
        return Responses.success(customerConverter.toDTO(customerService.lost(id, customerVO, user)));
    }

    /**
     * 寻回客户
     *
     * @param id         客户id
     * @param customerVO 客户信息
     * @param token      操作用户token
     * @return
     * @throws AccessException
     * @throws QueryException
     */
    @PatchMapping("/retrieve/{id}")
    public Response retrieve(@PathVariable Long id, @RequestBody CustomerVO customerVO, String token)
            throws AccessException, QueryException, InvalidException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerRetrievePermissions(user, token);
        return Responses.success(customerConverter.toDTO(customerService.retrieve(id, user)));
    }
}