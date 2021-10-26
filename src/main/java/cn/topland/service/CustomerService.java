package cn.topland.service;

import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.dao.gateway.CustomerGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.CustomerDO;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static cn.topland.entity.Customer.Action;
import static cn.topland.entity.Customer.Status;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private OperationGateway operationGateway;

    public Customer get(Long id) {

        return repository.getById(id);
    }

    @Transactional
    public CustomerDO add(CustomerVO customerVO, User creator) throws InternalException {

        validateNameUnique(customerVO.getName());
        CustomerDO customer = customerGateway.save(createCustomer(customerVO, creator), creator.getAccessToken());
        saveOperation(customer.getId(), Action.CREATE, creator, null, creator.getAccessToken());
        return customer;
    }

    @Transactional
    public CustomerDO update(Customer customer, CustomerVO customerVO, User editor) throws InternalException {

        validateNameUnique(customerVO.getName(), customer.getId());
        CustomerDO customerDO = customerGateway.update(updateCustomer(customer, customerVO, editor), editor.getAccessToken());
        saveOperation(customerDO.getId(), Action.UPDATE, editor, null, editor.getAccessToken());
        return customerDO;
    }

    @Transactional
    public CustomerDO lost(Long id, CustomerVO customerVO, User editor) throws InternalException {

        CustomerDO customer = customerGateway.update(lostCustomer(repository.getById(id), editor), editor.getAccessToken());
        saveOperation(id, Action.LOST, editor, customerVO.getLostReason(), editor.getAccessToken());
        return customer;
    }

    @Transactional
    public CustomerDO retrieve(Long id, User editor) throws InternalException {

        CustomerDO customer = customerGateway.update(retrieveCustomer(repository.getById(id), editor), editor.getAccessToken());
        saveOperation(id, Action.RETRIEVE, editor, null, editor.getAccessToken());
        return customer;
    }

    private void validateNameUnique(String name, Long id) {

        if (repository.existsByNameAndIdNot(name, id)) {

            throw new UniqueException("客户名称" + "[" + name + "]" + "重复");
        }
    }

    private void validateNameUnique(String name) throws UniqueException {

        if (repository.existsByName(name)) {

            throw new UniqueException("客户名称" + "[" + name + "]" + "重复");
        }
    }

    private Customer retrieveCustomer(Customer customer, User editor) {

        customer.setStatus(Status.COOPERATING);
        customer.setEditor(editor);
        customer.setLastUpdateTime(LocalDateTime.now());
        return customer;
    }

    private Customer lostCustomer(Customer customer, User editor) {

        customer.setStatus(Status.LOST);
        customer.setEditor(editor);
        customer.setLastUpdateTime(LocalDateTime.now());
        return customer;
    }

    private Customer updateCustomer(Customer customer, CustomerVO customerVO, User editor) {

        composeCustomer(customerVO, customer);
        customer.setEditor(editor);
        customer.setLastUpdateTime(LocalDateTime.now());
        return customer;
    }

    private Customer createCustomer(CustomerVO customerVO, User creator) {

        Customer customer = new Customer();
        composeCustomer(customerVO, customer);
        customer.setCreator(creator);
        customer.setEditor(creator);
        return customer;
    }

    private Customer createCustomer(CustomerVO customerVO, List<Contact> contacts, User creator) {

        Customer customer = new Customer();
        composeCustomer(customerVO, customer);
        customer.setContacts(contacts);
        customer.setCreator(creator);
        customer.setEditor(creator);
        return customer;
    }

    private void saveOperation(Long id, Action action, User creator, String remark, String accessToken) throws InternalException {

        operationGateway.save(createOperation(id, action, creator, remark), accessToken);
    }

    private Operation createOperation(Long id, Action action, User creator, String remark) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.CUSTOMER);
        operation.setAction(action.name());
        operation.setModuleId(String.valueOf(id));
        operation.setRemark(remark);
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private void composeCustomer(CustomerVO customerVO, Customer customer) {

        customer.setParent(getCustomer(customerVO.getParent()));
        customer.setSeller(getUser(customerVO.getSeller()));
        customer.setName(customerVO.getName());
        customer.setBusiness(customerVO.getBusiness());
        customer.setSource(customerVO.getSource());
        customer.setType(customerVO.getType());
        customer.setInvoice(createInvoice(customerVO));
    }

    private Invoice createInvoice(CustomerVO customer) {

        Invoice invoice = new Invoice();
        invoice.setInvoiceType(customer.getInvoiceType());
        invoice.setIdentity(customer.getIdentity());
        invoice.setPostAddress(customer.getPostAddress());
        invoice.setRegisterAddress(customer.getRegisterAddress());
        invoice.setAccount(customer.getAccount());
        invoice.setMobile(customer.getMobile());
        invoice.setBank(customer.getBank());
        return invoice;
    }

    private Customer getCustomer(Long customerId) {

        return customerId != null
                ? repository.getById(customerId)
                : null;
    }

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }
}