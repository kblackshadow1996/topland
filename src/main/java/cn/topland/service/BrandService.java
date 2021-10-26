package cn.topland.service;

import cn.topland.dao.BrandRepository;
import cn.topland.dao.CustomerRepository;
import cn.topland.dao.OperationRepository;
import cn.topland.dao.UserRepository;
import cn.topland.dao.gateway.BrandGateway;
import cn.topland.dao.gateway.OperationGateway;
import cn.topland.entity.Brand;
import cn.topland.entity.Customer;
import cn.topland.entity.Operation;
import cn.topland.entity.User;
import cn.topland.entity.directus.BrandDO;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.QueryException;
import cn.topland.util.exception.UniqueException;
import cn.topland.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    public Brand get(Long id) throws QueryException {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("品牌不存在");
        }
        return repository.getById(id);
    }

    @Transactional
    public BrandDO add(BrandVO brandVO, User creator) throws InternalException {

        validateNameUnique(brandVO.getName());
        BrandDO brandDO = brandGateway.save(createBrand(brandVO, creator), creator.getAccessToken());
        saveOperation(brandDO.getId(), Action.CREATE, creator);
        return brandDO;
    }

    @Transactional
    public BrandDO update(Brand brand, BrandVO brandVO, User editor) throws InternalException {

        validateNameUnique(brandVO.getName(), brand.getId());
        BrandDO brandDO = brandGateway.update(updateBrand(brand, brandVO, editor), editor.getAccessToken());
        saveOperation(brandDO.getId(), Action.UPDATE, editor);
        return brandDO;
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

    private void saveOperation(Long id, Action action, User creator) throws InternalException {

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