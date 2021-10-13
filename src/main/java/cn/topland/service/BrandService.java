package cn.topland.service;

import cn.topland.dao.BrandRepository;
import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.Brand;
import cn.topland.entity.Customer;
import cn.topland.entity.Operation;
import cn.topland.entity.User;
import cn.topland.util.UniqueException;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static cn.topland.entity.Brand.*;

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
    private ContactService contactService;

    @Transactional
    public Brand add(BrandVO brandVO, User creator) {

        try {

            Brand brand = repository.saveAndFlush(createBrand(brandVO, creator));
            saveOperation(brand.getId(), Action.CREATE, creator);
            return brand;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    @Transactional
    public Brand update(Long id, BrandVO brandVO, User editor) {

        try {

            Brand persistBrand = repository.getById(id);
            Brand brand = repository.saveAndFlush(updateBrand(persistBrand, brandVO, editor));
            saveOperation(brand.getId(), Action.UPDATE, editor);
            return brand;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    private Brand updateBrand(Brand brand, BrandVO brandVO, User editor) {

        composeBrand(brand, brandVO);
        brand.setContacts(contactService.updateContacts(brand.getContacts(), brandVO.getContacts()));
        brand.setEditor(editor);
        brand.setLastUpdateTime(LocalDateTime.now());
        return brand;
    }

    private Brand createBrand(BrandVO brandVO, User creator) {

        Brand brand = new Brand();
        composeBrand(brand, brandVO);
        brand.setContacts(contactService.createContacts(brandVO.getContacts()));
        brand.setCreator(creator);
        brand.setEditor(creator);
        return brand;
    }

    private void saveOperation(Long id, Action action, User creator) {

        operationRepository.saveAndFlush(createOperation(id, action, creator));
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