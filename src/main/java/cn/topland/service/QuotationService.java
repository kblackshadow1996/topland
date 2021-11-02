package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.dao.gateway.QuotationGateway;
import cn.topland.dao.gateway.QuotationServiceGateway;
import cn.topland.entity.Package;
import cn.topland.entity.*;
import cn.topland.entity.directus.QuotationDO;
import cn.topland.entity.directus.QuotationServiceDO;
import cn.topland.util.UUIDGenerator;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.QueryException;
import cn.topland.util.pdf.HtmlToPdfOperation;
import cn.topland.util.pdf.HtmlToPdfParams;
import cn.topland.util.pdf.HtmlToPdfParamsFactory;
import cn.topland.util.pdf.QuotationPdfOperation;
import cn.topland.vo.QuotationServiceVO;
import cn.topland.vo.QuotationVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    @Value("${tmp.dir}")
    private String temDir;

    @Autowired
    private QuotationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private QuotationServiceRepository quotationServiceRepository;

    @Autowired
    private QuotationServiceGateway quotationServiceGateway;

    @Autowired
    private QuotationGateway quotationGateway;

    public Quotation get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("报价[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public QuotationDO add(QuotationVO quotationVO, User creator) {

        QuotationDO quotationDO = quotationGateway.save(createQuotation(quotationVO, creator), creator.getAccessToken());
        quotationDO.setServices(listServices(updateServices(List.of(), quotationVO.getServices(), quotationDO.getId(), creator.getAccessToken())));
        return quotationDO;
    }

    public QuotationDO update(Long id, QuotationVO quotationVO, User editor) {

        Quotation quotation = get(id);
        QuotationDO quotationDO = quotationGateway.update(updateQuotation(quotation, quotationVO, editor), editor.getAccessToken());
        quotationDO.setServices(listServices(updateServices(quotation.getServices(), quotationVO.getServices(), id, editor.getAccessToken())));
        return quotationDO;
    }

    public byte[] downloadPdf(String html, String title, String number, LocalDate date) {

        try {

            if (!Files.exists(new File(temDir).toPath())) {

                new File(temDir).mkdirs();
            }
            String randomName = UUIDGenerator.generate();
            String htmlPath = temDir + "/" + randomName + ".html";
            String tmp = temDir + "/" + randomName + ".tmp.pdf";
            String dest = temDir + "/" + randomName + ".pdf";

            // 存html
            FileUtils.writeStringToFile(new File(htmlPath), html, StandardCharsets.UTF_8);

            // 生成pdf
            HtmlToPdfParams params = new HtmlToPdfParamsFactory().quotation();
            new HtmlToPdfOperation(params).apply(htmlPath, tmp);

            // 处理pdf
            QuotationPdfOperation pdfOperation = getPdfOperation(title, number, date);
            pdfOperation.apply(tmp, dest);

            return FileUtils.readFileToByteArray(new File(dest));
        } catch (IOException e) {

            throw new InternalException("pdf生成失败,请稍后再试");
        }
    }

    private List<Long> listServices(List<QuotationServiceDO> services) {

        return CollectionUtils.isEmpty(services)
                ? List.of()
                : services.stream().filter(service -> service.getQuotation() != null)
                .map(QuotationServiceDO::getId).collect(Collectors.toList());
    }

    private QuotationPdfOperation getPdfOperation(String title, String number, LocalDate date) {

        return QuotationPdfOperation.builder()
                .title(title)
                .number(number)
                .date(date)
                .build();
    }

    private Quotation createQuotation(QuotationVO quotationVO, User creator) {

        Quotation quotation = new Quotation();
        composeQuotation(quotation, quotationVO);
        quotation.setIdentity(createIdentity(creator));
        quotation.setCreator(creator);
        quotation.setEditor(creator);
        return quotation;
    }

    private String createIdentity(User creator) {

        // 该用户当天生成报价合同的数量
        Long count = repository.countByCreatorAndCreateTimeBetween(creator,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        // 当日时间：年月日如20211212
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 工号
        String employeeId = creator.getEmployeeId();
        // 时间+工号+第n份合同
        return date + employeeId + (count + 1);
    }

    private Quotation updateQuotation(Quotation quotation, QuotationVO quotationVO, User editor) {

        composeQuotation(quotation, quotationVO);
        quotation.setEditor(editor);
        quotation.setLastUpdateTime(LocalDateTime.now());
        return quotation;
    }

    private void composeQuotation(Quotation quotation, QuotationVO quotationVO) {

        // 基本信息
        quotation.setTitle(quotationVO.getTitle());
        quotation.setSubtotal(quotationVO.getSubtotal());
        quotation.setDiscount(quotationVO.getDiscount());
        quotation.setComment(createComment(quotationVO));
        quotation.setExplanations(quotationVO.getExplanations());

        // 其他关联表信息
        quotation.setServicePackage(quotationVO.getServicePackage() == null ? null : getPackage(quotationVO.getServicePackage()));
        quotation.setCustomer(quotationVO.getCustomer() == null ? null : getCustomer(quotationVO.getCustomer()));
        quotation.setBrand(quotationVO.getBrand() == null ? null : getBrand(quotationVO.getBrand()));
        quotation.setSeller(quotationVO.getSeller() == null ? null : getUser(quotationVO.getSeller()));
    }

    private List<QuotationServiceDO> updateServices(List<cn.topland.entity.QuotationService> services,
                                                    List<QuotationServiceVO> serviceVOs, Long quotation, String accessToken) {

        List<Long> serviceIds = serviceVOs.stream().map(QuotationServiceVO::getService).collect(Collectors.toList());
        Map<Long, cn.topland.entity.Service> serviceMap = serviceRepository.findAllById(serviceIds).stream()
                .collect(Collectors.toMap(IdEntity::getId, s -> s));
        Map<Long, cn.topland.entity.QuotationService> quotationServiceMap = services.stream()
                .collect(Collectors.toMap(IdEntity::getId, s -> s));
        List<cn.topland.entity.QuotationService> quotationServices = new ArrayList<>();
        List<cn.topland.entity.QuotationService> updates = new ArrayList<>();
        for (QuotationServiceVO serviceVO : serviceVOs) {

            cn.topland.entity.QuotationService service;
            if (quotationServiceMap.containsKey(serviceVO.getId())) {

                service = quotationServiceMap.get(serviceVO.getId());
                updates.add(service);
                updateService(service, serviceVO, serviceMap.get(serviceVO.getService()));
            } else {

                service = createService(serviceVO, serviceMap.get(serviceVO.getService()));
            }
            service.setQuotation(quotation);
            quotationServices.add(service);
        }
        List<cn.topland.entity.QuotationService> deletes = (List<cn.topland.entity.QuotationService>) CollectionUtils.removeAll(services, updates);
        deletes.forEach(delete -> {
            // 解除关联
            delete.setQuotation(null);
        });
        quotationServices.addAll(deletes);
        return quotationServiceGateway.saveAll(quotationServices, accessToken);
    }

    private void updateService(cn.topland.entity.QuotationService quotationService,
                               QuotationServiceVO serviceVO,
                               cn.topland.entity.Service service) {

        quotationService.setAmount(serviceVO.getAmount());
        quotationService.setExplanation(serviceVO.getExplanation());
        quotationService.setPrice(serviceVO.getPrice());
        quotationService.setUnit(serviceVO.getUnit());
        quotationService.setService(service);
    }

    private cn.topland.entity.QuotationService createService(QuotationServiceVO serviceVO, cn.topland.entity.Service service) {

        var quotationService = new cn.topland.entity.QuotationService();
        quotationService.setAmount(serviceVO.getAmount());
        quotationService.setExplanation(serviceVO.getExplanation());
        quotationService.setPrice(serviceVO.getPrice());
        quotationService.setUnit(serviceVO.getUnit());
        quotationService.setService(service);
        return quotationService;
    }

    private QuotationComment createComment(QuotationVO quotationVO) {

        QuotationComment comment = new QuotationComment();
        comment.setSubtotalComment(quotationVO.getSubtotalComment());
        comment.setDiscountComment(quotationVO.getDiscountComment());
        comment.setTotalComment(quotationVO.getTotalComment());
        return comment;
    }

    private Customer getCustomer(Long customerId) {

        if (customerId == null || !customerRepository.existsById(customerId)) {

            throw new QueryException("客户[id:" + customerId + "]不存在");
        }
        return customerRepository.getById(customerId);
    }

    private Brand getBrand(Long brandId) {

        if (brandId == null || !brandRepository.existsById(brandId)) {

            throw new QueryException("品牌[id:" + brandId + "]不存在");
        }
        return brandRepository.getById(brandId);
    }

    private User getUser(Long userId) {

        if (userId == null || !userRepository.existsById(userId)) {

            throw new QueryException("用户[id:" + userId + "]不存在");
        }
        return userRepository.getById(userId);
    }

    private Package getPackage(Long packageId) {

        if (packageId == null || !packageRepository.existsById(packageId)) {

            throw new QueryException("套餐[id:" + packageId + "]不存在");
        }
        return packageRepository.getById(packageId);
    }
}