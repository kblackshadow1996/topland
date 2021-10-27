package cn.topland.service;

import cn.topland.dao.BrandRepository;
import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.dao.gateway.BrandGateway;
import cn.topland.dao.gateway.ContactGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.BrandDO;
import cn.topland.entity.directus.ContactDO;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.BrandVO;
import cn.topland.vo.ContactVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.topland.entity.Brand.Action;

@Service
public class BrandService {

    @Autowired
    private BrandRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BrandGateway brandGateway;

    @Autowired
    private OperationGateway operationGateway;

    @Autowired
    private ContactGateway contactGateway;

    public Brand get(Long id) {

        return repository.getById(id);
    }

    public BrandDO add(BrandVO brandVO, User creator) {

        validateNameUnique(brandVO.getName());
        BrandDO brandDO = brandGateway.save(createBrand(brandVO, creator), creator.getAccessToken());
        brandDO.setContacts(listContacts(updateBrandContacts(List.of(), brandVO.getContacts(), brandDO.getId(), creator.getAccessToken())));
        saveOperation(brandDO.getId(), Action.CREATE, creator);
        return brandDO;
    }

    public BrandDO update(Long id, BrandVO brandVO, User editor) {

        Brand brand = repository.getById(id);
        validateNameUnique(brandVO.getName(), brand.getId());
        BrandDO brandDO = brandGateway.update(updateBrand(brand, brandVO, editor), editor.getAccessToken());
        brandDO.setContacts(listContacts(updateBrandContacts(brand.getContacts(), brandVO.getContacts(), id, editor.getAccessToken())));
        saveOperation(brandDO.getId(), Action.UPDATE, editor);
        return brandDO;
    }

    private List<Long> listContacts(List<ContactDO> contacts) {

        return CollectionUtils.isEmpty(contacts)
                ? List.of()
                : contacts.stream().filter(contact -> contact.getBrand() != null)
                .map(ContactDO::getId).collect(Collectors.toList());
    }

    public List<ContactDO> updateBrandContacts(List<Contact> contacts, List<ContactVO> contactVOs, Long brand, String token) {

        contacts = CollectionUtils.isEmpty(contacts) ? List.of() : contacts;
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(IdEntity::getId, contact -> contact));
        List<Contact> brandContacts = new ArrayList<>();
        List<Contact> updates = new ArrayList<>();
        for (ContactVO contactVO : contactVOs) {

            if (contactMap.containsKey(contactVO.getId())) {

                Contact contact = contactMap.get(contactVO.getId());
                updates.add(contact);
                brandContacts.add(updateContact(contact, contactVO, brand));
            } else {

                brandContacts.add(createContact(contactVO, brand));
            }
        }
        List<Contact> deletes = (List<Contact>) CollectionUtils.removeAll(contacts, updates);
        deletes.forEach(delete -> {
            // 解除关联
            delete.setBrand(null);
        });
        brandContacts.addAll(deletes);
        return contactGateway.saveAll(brandContacts, token);
    }

    private Contact updateContact(Contact contact, ContactVO contactVO, Long brand) {

        Contact con = composeContact(contact, contactVO);
        con.setBrand(brand);
        return con;
    }

    private Contact createContact(ContactVO contactVO, Long brand) {

        Contact contact = composeContact(new Contact(), contactVO);
        contact.setBrand(brand);
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

    private void validateNameUnique(String name) {

        if (repository.existsByName(name)) {

            throw new UniqueException("品牌名称" + "[" + name + "]" + "重复");
        }
    }

    private void validateNameUnique(String name, Long id) {

        if (repository.existsByNameAndIdNot(name, id)) {

            throw new UniqueException("品牌名称" + "[" + name + "]" + "重复");
        }
    }

    private Brand updateBrand(Brand brand, BrandVO brandVO, User editor) {

        composeBrand(brand, brandVO);
        brand.setEditor(editor);
        brand.setLastUpdateTime(LocalDateTime.now());
        return brand;
    }

    private Brand createBrand(BrandVO brandVO, User creator) {

        Brand brand = new Brand();
        composeBrand(brand, brandVO);
        brand.setCreator(creator);
        brand.setEditor(creator);
        return brand;
    }

    private void saveOperation(Long id, Action action, User creator) {

        operationGateway.save(createOperation(id, action, creator), creator.getAccessToken());
    }

    private Operation createOperation(Long id, Action action, User creator) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.BRAND);
        operation.setAction(action.name());
        operation.setModuleId(String.valueOf(id));
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private void composeBrand(Brand brand, BrandVO brandVO) {

        brand.setSeller(getUser(brandVO.getSeller()));
        brand.setProducer(getUser(brandVO.getProducer()));
        brand.setName(brandVO.getName());
        brand.setCustomer(getCustomer(brandVO.getCustomer()));
        brand.setBusiness(brandVO.getBusiness());
    }

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }

    private Customer getCustomer(Long customerId) {

        return customerId != null
                ? customerRepository.getById(customerId)
                : null;
    }
}