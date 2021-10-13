package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.CustomerConverter;
import cn.topland.entity.Contact;
import cn.topland.entity.Customer;
import cn.topland.entity.User;
import cn.topland.service.ContactService;
import cn.topland.service.CustomerService;
import cn.topland.service.UserService;
import cn.topland.util.AccessException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.UniqueException;
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

    @PostMapping("/add")
    public Response add(@RequestBody CustomerVO customerVO) {

        try {

            User user = userService.get(customerVO.getCreator());
            validator.validateCustomerCreatePermissions(user.getRole());
            return Responses.success(customerConverter.toDTO(
                    customerService.add(customerVO, contactService.createContacts(customerVO.getContacts()), user))
            );
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FAILED_VALIDATION, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody CustomerVO customerVO) {

        try {

            User user = userService.get(customerVO.getCreator());
            validator.validateCustomerUpdatePermissions(user.getRole());
            Customer customer = customerService.get(id);
            List<Contact> contacts = contactService.updateContacts(customer.getContacts(), customerVO.getContacts());
            return Responses.success(customerConverter.toDTO(customerService.update(customer, customerVO, contacts, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (UniqueException e) {

            return Responses.fail(Response.FAILED_VALIDATION, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/lost/{id}")
    public Response lost(@PathVariable Long id, @RequestBody CustomerVO customer) {

        try {

            User user = userService.get(customer.getCreator());
            validator.validateCustomerLostPermissions(user.getRole());
            return Responses.success(customerConverter.toDTO(customerService.lost(id, customer, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/retrieve/{id}")
    public Response retrieve(@PathVariable Long id, @RequestBody CustomerVO customer) {

        try {

            User user = userService.get(customer.getCreator());
            validator.validateCustomerRetrievePermissions(user.getRole());
            return Responses.success(customerConverter.toDTO(customerService.retrieve(id, user)));
        } catch (AccessException e) {

            return Responses.fail(Response.FORBIDDEN, e.getMessage());
        } catch (Exception e) {

            return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}