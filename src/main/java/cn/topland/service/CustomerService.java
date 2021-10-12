package cn.topland.service;

import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.Customer;
import cn.topland.entity.Invoice;
import cn.topland.entity.Operation;
import cn.topland.entity.User;
import cn.topland.util.UniqueException;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ContactService contactService;

    @Transactional
    public Customer add(CustomerVO customerVO, User creator) throws UniqueException {

        try {

            Customer customer = repository.saveAndFlush(createCustomer(customerVO, creator));
            saveCreateOperation(creator, customer.getId());
            return customer;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    @Transactional
    public Customer update(CustomerVO customerVO, User editor) {

        try {

            Customer persistCustomer = repository.getById(customerVO.getId());
            Customer customer = repository.saveAndFlush(updateCustomer(persistCustomer, customerVO, editor));
            saveUpdateOperation(editor, customer.getId());
            return customer;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    private void saveCreateOperation(User creator, Long id) {

        operationRepository.saveAndFlush(createAddCustomerOperation(creator, id));
    }

    private void saveUpdateOperation(User editor, Long id) {

        operationRepository.saveAndFlush(createUpdateCustomerOperation(editor, id));
    }

    private Operation createAddCustomerOperation(User creator, Long id) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.CUSTOMER);
        operation.setModuleId(String.valueOf(id));
        operation.setAction(Customer.Action.CREATE.name());
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private Operation createUpdateCustomerOperation(User editor, Long id) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.CUSTOMER);
        operation.setModuleId(String.valueOf(id));
        operation.setAction(Customer.Action.UPDATE.name());
        operation.setCreator(editor);
        operation.setEditor(editor);
        return operation;
    }

    private Customer updateCustomer(Customer customer, CustomerVO customerVO, User editor) {

        composeCustomer(customerVO, customer);
        customer.setContacts(contactService.updateContacts(customer.getContacts(), customerVO.getContacts()));
        customer.setEditor(editor);
        customer.setLastUpdateTime(LocalDateTime.now());
        return customer;
    }

    private Customer createCustomer(CustomerVO customerVO, User creator) {

        Customer customer = new Customer();
        composeCustomer(customerVO, customer);
        customer.setContacts(contactService.createContacts(customerVO.getContacts()));
        customer.setCreator(creator);
        customer.setEditor(creator);
        return customer;
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