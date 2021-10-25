package cn.topland.service;

import cn.topland.dao.BrandRepository;
import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.*;
import cn.topland.util.exception.QueryException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    public Brand get(Long id) throws QueryException {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("品牌不存在");
        }
        return repository.getById(id);
    }

    @Transactional
    public Brand add(BrandVO brandVO, List<Contact> contacts, User creator) {

        validateNameUnique(brandVO.getName());
        Brand brand = repository.saveAndFlush(createBrand(brandVO, contacts, creator));
        saveOperation(brand.getId(), Action.CREATE, creator);
        return brand;
    }

    @Transactional
    public Brand update(Brand brand, BrandVO brandVO, List<Contact> contacts, User editor) {

        validateNameUnique(brandVO.getName(), brand.getId());
        repository.saveAndFlush(updateBrand(brand, brandVO, contacts, editor));
        saveOperation(brand.getId(), Action.UPDATE, editor);
        return brand;
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

    private Brand updateBrand(Brand brand, BrandVO brandVO, List<Contact> contacts, User editor) {

        composeBrand(brand, brandVO);
        brand.setContacts(contacts);
        brand.setEditor(editor);
        brand.setLastUpdateTime(LocalDateTime.now());
        return brand;
    }

    private Brand createBrand(BrandVO brandVO, List<Contact> contacts, User creator) {

        Brand brand = new Brand();
        composeBrand(brand, brandVO);
        brand.setContacts(contacts);
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