package cn.topland.service;

import cn.topland.dao.BrandRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.Brand;
import cn.topland.entity.Operation;
import cn.topland.entity.User;
import cn.topland.util.UniqueException;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BrandService {

    @Autowired
    private BrandRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ContactService contactService;

    @Transactional
    public Brand add(BrandVO brandVO, User creator) {

        try {

            Brand brand = repository.saveAndFlush(createBrand(brandVO, creator));
            saveCreateOperation(creator, brand.getId());
            return brand;
        } catch (DataIntegrityViolationException e) {

            throw new UniqueException();
        }
    }

    @Transactional
    public Brand update(BrandVO brandVO, User editor) {

        try {

            Brand persistBrand = repository.getById(brandVO.getId());
            Brand brand = repository.saveAndFlush(updateBrand(persistBrand, brandVO, editor));
            saveUpdateOperation(editor, brand.getId());
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

    private void composeBrand(Brand brand, BrandVO brandVO) {

        brand.setSeller(getUser(brandVO.getSeller()));
        brand.setProducer(getUser(brandVO.getProducer()));
        brand.setName(brandVO.getName());
        brand.setBusiness(brandVO.getBusiness());
    }

    private void saveCreateOperation(User creator, Long id) {

        operationRepository.saveAndFlush(createAddBrandOperation(creator, id));
    }

    private void saveUpdateOperation(User editor, Long id) {

        operationRepository.saveAndFlush(createUpdateBrandOperation(editor, id));
    }

    private Operation createUpdateBrandOperation(User creator, Long id) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.BRAND);
        operation.setModuleId(String.valueOf(id));
        operation.setAction(Brand.Action.UPDATE.name());
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private Operation createAddBrandOperation(User creator, Long id) {

        Operation operation = new Operation();
        operation.setModule(Operation.Module.BRAND);
        operation.setModuleId(String.valueOf(id));
        operation.setAction(Brand.Action.CREATE.name());
        operation.setCreator(creator);
        operation.setEditor(creator);
        return operation;
    }

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }
}