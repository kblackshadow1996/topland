package cn.topland.service;

import cn.topland.dao.*;
import cn.topland.entity.Package;
import cn.topland.entity.*;
import cn.topland.util.UUIDGenerator;
import cn.topland.util.exception.InternalException;
import cn.topland.util.pdf.HtmlToPdfOperation;
import cn.topland.util.pdf.HtmlToPdfParams;
import cn.topland.util.pdf.HtmlToPdfParamsFactory;
import cn.topland.util.pdf.QuotationPdfOperation;
import cn.topland.vo.QuotationServiceVO;
import cn.topland.vo.QuotationVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public Quotation add(QuotationVO quotationVO, User creator) {

        return repository.saveAndFlush(createQuotation(quotationVO, creator));
    }

    @Transactional
    public Quotation update(Long id, QuotationVO quotationVO, User editor) {

        return repository.saveAndFlush(updateQuotation(repository.getById(id), quotationVO, editor));
    }

    public byte[] downloadPdf(String html, String title, String number, LocalDate date) throws InternalException {

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
        quotation.setServices(createServices(quotationVO.getServices()));
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
        quotation.setServices(updateServices(quotation.getServices(), quotationVO.getServices()));
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
        quotation.setServicePackage(getPackage(quotationVO.getServicePackage()));
        quotation.setCustomer(getCustomer(quotationVO.getCustomer()));
        quotation.setBrand(getBrand(quotationVO.getBrand()));
        quotation.setSeller(getUser(quotationVO.getSeller()));
    }

    private List<cn.topland.entity.QuotationService> updateServices(List<cn.topland.entity.QuotationService> services,
                                                                    List<QuotationServiceVO> serviceVOs) {

        List<Long> serviceIds = serviceVOs.stream().map(QuotationServiceVO::getService).collect(Collectors.toList());
        Map<Long, cn.topland.entity.Service> serviceMap = serviceRepository.findAllById(serviceIds).stream()
                .collect(Collectors.toMap(IdEntity::getId, s -> s));
        Map<Long, cn.topland.entity.QuotationService> quotationServiceMap = services.stream()
                .collect(Collectors.toMap(IdEntity::getId, s -> s));
        List<cn.topland.entity.QuotationService> quotationServices = serviceVOs.stream().map(serviceVO -> {

            return quotationServiceMap.containsKey(serviceVO.getId())
                    ? updateService(quotationServiceMap.get(serviceVO.getId()), serviceVO, serviceMap.get(serviceVO.getService()))
                    : createService(serviceVO, serviceMap.get(serviceVO.getService()));
        }).collect(Collectors.toList());
        return quotationServiceRepository.saveAllAndFlush(quotationServices);
    }

    private List<cn.topland.entity.QuotationService> createServices(List<QuotationServiceVO> services) {

        List<Long> serviceIds = services.stream().map(QuotationServiceVO::getService).collect(Collectors.toList());
        Map<Long, cn.topland.entity.Service> serviceMap = serviceRepository.findAllById(serviceIds).stream()
                .collect(Collectors.toMap(IdEntity::getId, s -> s));
        List<cn.topland.entity.QuotationService> quotationServices = services.stream()
                .map(serviceVO -> createService(serviceVO, serviceMap.get(serviceVO.getService())))
                .collect(Collectors.toList());
        return quotationServiceRepository.saveAllAndFlush(quotationServices);
    }

    private cn.topland.entity.QuotationService updateService(cn.topland.entity.QuotationService quotationService,
                                                             QuotationServiceVO serviceVO,
                                                             cn.topland.entity.Service service) {

        quotationService.setAmount(serviceVO.getAmount());
        quotationService.setExplanation(serviceVO.getExplanation());
        quotationService.setPrice(serviceVO.getPrice());
        quotationService.setUnit(serviceVO.getUnit());
        quotationService.setService(service);
        return quotationService;
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

    private User getUser(Long userId) {

        return userId != null
                ? userRepository.getById(userId)
                : null;
    }

    private Brand getBrand(Long brandId) {

        return brandId != null
                ? brandRepository.getById(brandId)
                : null;
    }

    private Customer getCustomer(Long customerId) {

        return customerId != null
                ? customerRepository.getById(customerId)
                : null;
    }

    private Package getPackage(Long packageId) {

        return packageId != null
                ? packageRepository.getById(packageId)
                : null;
    }
}