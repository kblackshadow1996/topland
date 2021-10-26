package cn.topland.controller;

import cn.topland.controller.validator.PermissionValidator;
import cn.topland.dto.converter.CustomerConverter;
import cn.topland.entity.Contact;
import cn.topland.entity.Customer;
import cn.topland.entity.IdEntity;
import cn.topland.entity.User;
import cn.topland.entity.directus.ContactDO;
import cn.topland.entity.directus.CustomerDO;
import cn.topland.entity.directus.DirectusSimpleIdEntity;
import cn.topland.service.ContactService;
import cn.topland.service.CustomerService;
import cn.topland.service.UserService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.InvalidException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.CustomerVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerCreatePermissions(user, token);
        CustomerDO customerDO = customerService.add(customerVO, user);
        customerDO.setContacts(listContactDOs(contactService.createCustomerContacts(customerVO.getContacts(), customerDO.getId(), token)));
        return Responses.success(customerConverter.toDTO(customerDO));
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
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerUpdatePermissions(user, token);
        Customer customer = customerService.get(id);
        CustomerDO customerDO = customerService.update(customer, customerVO, user);
        List<ContactDO> contacts = contactService.updateCustomerContacts(customer.getContacts(), customerVO.getContacts(), id, token);
        customerDO.setContacts(listContactDOs(contacts));
        return Responses.success(customerConverter.toDTO(customerDO));
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
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerLostPermissions(user, token);
        CustomerDO customerDO = customerService.lost(id, customerVO, user);
        customerDO.setContacts(listContacts(customerService.get(id).getContacts()));
        return Responses.success(customerConverter.toDTO(customerDO));
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
            throws AccessException, QueryException, InvalidException, InternalException {

        User user = userService.get(customerVO.getCreator());
        validator.validateCustomerRetrievePermissions(user, token);
        CustomerDO customerDO = customerService.retrieve(id, user);
        customerDO.setContacts(listContacts(customerService.get(id).getContacts()));
        return Responses.success(customerConverter.toDTO(customerDO));
    }

    private List<Long> listContacts(List<Contact> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().map(IdEntity::getId).collect(Collectors.toList());
    }

    private List<Long> listContactDOs(List<ContactDO> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().filter(contactDO -> contactDO.getCustomer() != null)
                .map(DirectusSimpleIdEntity::getId)
                .collect(Collectors.toList());
    }
}