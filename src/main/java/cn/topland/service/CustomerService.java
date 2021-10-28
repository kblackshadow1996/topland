package cn.topland.service;

import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.dao.gateway.ContactGateway;
import cn.topland.dao.gateway.CustomerGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.ContactDO;
import cn.topland.entity.directus.CustomerDO;
import cn.topland.util.exception.QueryException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.ContactVO;
import cn.topland.vo.CustomerVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ContactGateway contactGateway;

    public Customer get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("客户[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public CustomerDO add(CustomerVO customerVO, User creator) {

        validateNameUnique(customerVO.getName());
        CustomerDO customer = customerGateway.save(createCustomer(customerVO, creator), creator.getAccessToken());
        customer.setContacts(listContacts(updateCustomerContacts(List.of(), customerVO.getContacts(), customer.getId(), creator.getAccessToken())));
        saveOperation(customer.getId(), Action.CREATE, creator, null);
        return customer;
    }

    public CustomerDO update(Long id, CustomerVO customerVO, User editor) {

        Customer customer = get(id);
        validateNameUnique(customerVO.getName(), id);
        CustomerDO customerDO = customerGateway.update(updateCustomer(customer, customerVO, editor), editor.getAccessToken());
        List<ContactDO> contactDOs = updateCustomerContacts(customer.getContacts(), customerVO.getContacts(), id, editor.getAccessToken());
        customerDO.setContacts(listContacts(contactDOs));
        saveOperation(id, Action.UPDATE, editor, null);
        return customerDO;
    }

    public CustomerDO lost(Long id, CustomerVO customerVO, User editor) {

        Customer customer = get(id);
        CustomerDO customerDO = customerGateway.update(lostCustomer(customer, editor), editor.getAccessToken());
        saveOperation(id, Action.LOST, editor, customerVO.getLostReason());
        customerDO.setContacts(contacts(customer.getContacts()));
        return customerDO;
    }

    public CustomerDO retrieve(Long id, User editor) {

        Customer customer = get(id);
        CustomerDO customerDO = customerGateway.update(retrieveCustomer(customer, editor), editor.getAccessToken());
        saveOperation(id, Action.RETRIEVE, editor, null);
        customerDO.setContacts(contacts(customer.getContacts()));
        return customerDO;
    }

    private List<Long> contacts(List<Contact> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().map(IdEntity::getId).collect(Collectors.toList());
    }

    private List<Long> listContacts(List<ContactDO> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().filter(contact -> contact.getCustomer() != null)
                .map(ContactDO::getId).collect(Collectors.toList());
    }

    private List<ContactDO> updateCustomerContacts(List<Contact> contacts, List<ContactVO> contactVOs, Long customer, String accessToken) {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        List<Contact> customerContacts = new ArrayList<>();
        List<Contact> updates = new ArrayList<>();
        for (ContactVO contactVO : contactVOs) {

            if (contactMap.containsKey(contactVO.getId())) {

                Contact contact = contactMap.get(contactVO.getId());
                updates.add(contact);
                customerContacts.add(updateContact(contact, contactVO, customer));
            } else {

                customerContacts.add(createContact(contactVO, customer));
            }
        }
        List<Contact> deletes = (List<Contact>) CollectionUtils.removeAll(contacts, updates);
        deletes.forEach(delete -> {
            // 解除关联
            delete.setCustomer(null);
        });
        customerContacts.addAll(deletes);
        return contactGateway.saveAll(customerContacts, accessToken);
    }

    private Contact updateContact(Contact contact, ContactVO contactVO, Long customer) {

        Contact con = composeContact(contact, contactVO);
        con.setCustomer(customer);
        return con;
    }

    private Contact createContact(ContactVO contactVO, Long customer) {

        Contact contact = composeContact(new Contact(), contactVO);
        contact.setCustomer(customer);
        return contact;
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

    private void validateNameUnique(String name, Long id) {

        if (repository.existsByNameAndIdNot(name, id)) {

            throw new UniqueException("客户名称[" + name + "]重复");
        }
    }

    private void validateNameUnique(String name) throws UniqueException {

        if (repository.existsByName(name)) {

            throw new UniqueException("客户名称[" + name + "]重复");
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

    private void saveOperation(Long id, Action action, User creator, String remark) {

        operationGateway.save(createOperation(id, action, creator, remark), creator.getAccessToken());
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

        customer.setParent(customerVO.getParent() == null ? null : get(customerVO.getParent()));
        customer.setSeller(customerVO.getSeller() == null ? null : getUser(customerVO.getSeller()));
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

    private User getUser(Long userId) {

        if (!userRepository.existsById(userId)) {

            throw new QueryException("用户[id:" + userId + "]不存在");
        }
        return userRepository.getById(userId);
    }
}