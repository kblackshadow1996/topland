package cn.topland.service;

import cn.topland.dao.ContactRepository;
import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.*;
import cn.topland.util.UniqueException;
import cn.topland.vo.ContactVO;
import cn.topland.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private OperationRepository operationRepository;

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
        customer.setContacts(updateContacts(customer, customerVO));
        customer.setEditor(editor);
        customer.setLastUpdateTime(LocalDateTime.now());
        return customer;
    }

    private Customer createCustomer(CustomerVO customerVO, User creator) {

        Customer customer = new Customer();
        composeCustomer(customerVO, customer);
        customer.setContacts(createContacts(customerVO.getContacts()));
        customer.setCreator(creator);
        customer.setEditor(creator);
        return customer;
    }

    private void composeCustomer(CustomerVO customerVO, Customer customer) {

        Customer parent = customerVO.getParent() != null
                ? repository.getById(customerVO.getParent())
                : null;
        customer.setParent(parent);
        User seller = customerVO.getSeller() != null
                ? userRepository.getById(customerVO.getSeller())
                : null;
        customer.setSeller(seller);
        customer.setName(customerVO.getName());
        customer.setBusiness(customerVO.getBusiness());
        customer.setSource(customerVO.getSource());
        customer.setType(customerVO.getType());
        customer.setInvoice(createInvoice(customerVO));
    }

    private List<Contact> createContacts(List<ContactVO> contactVOs) {

        List<Contact> contacts = contactVOs.stream().map(this::createContact).collect(Collectors.toList());
        return contactRepository.saveAllAndFlush(contacts);
    }

    private List<Contact> updateContacts(Customer customer, CustomerVO customerVO) {

        Map<Long, Contact> contacts = customer.getContacts().stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        return customerVO.getContacts().stream().map(contactVO -> {

            return contacts.containsKey(contactVO.getId())
                    ? updateContact(contacts.get(contactVO.getId()), contactVO)
                    : createContact(contactVO);
        }).collect(Collectors.toList());
    }

    private Contact updateContact(Contact contact, ContactVO contactVO) {

        return composeContact(contact, contactVO);
    }

    private Contact createContact(ContactVO contactVO) {

        return composeContact(new Contact(), contactVO);
    }

    private Contact composeContact(Contact contact, ContactVO contactVO) {

        contact.setName(contactVO.getName());
        contact.setGender(contactVO.getGender());
        contact.setMobile(contactVO.getMobile());
        contact.setAddress(contactVO.getAddress());
        contact.setDepartment(contactVO.getDepartment());
        contact.setPosition(contactVO.getPosition());
        contact.setRemark(contactVO.getRemark());
        return contact;
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
}