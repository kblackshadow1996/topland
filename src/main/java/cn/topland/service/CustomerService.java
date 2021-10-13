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
    private ContactService contactService;

    @Transactional
    public Customer add(CustomerVO customerVO, User creator) throws UniqueException {

        try {

            Customer customer = repository.saveAndFlush(createCustomer(customerVO, creator));
            saveOperation(customer.getId(), Action.CREATE, creator, null);
            return customer;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    @Transactional
    public Customer update(Long id, CustomerVO customerVO, User editor) {

        try {

            Customer persistCustomer = repository.getById(id);
            Customer customer = repository.saveAndFlush(updateCustomer(persistCustomer, customerVO, editor));
            saveOperation(id, Action.UPDATE, editor, null);
            return customer;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    @Transactional
    public Customer lost(Long id, CustomerVO customerVO, User editor) {

        Customer customer = repository.saveAndFlush(lostCustomer(repository.getById(id), editor));
        saveOperation(id, Action.LOST, editor, customerVO.getLostReason());
        return customer;
    }

    @Transactional
    public Customer retrieve(Long id, User editor) {

        Customer customer = repository.saveAndFlush(retrieveCustomer(repository.getById(id), editor));
        saveOperation(id, Action.RETRIEVE, editor, null);
        return customer;
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

    private void saveOperation(Long id, Action action, User creator, String remark) {

        operationRepository.saveAndFlush(createOperation(id, action, creator, remark));
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