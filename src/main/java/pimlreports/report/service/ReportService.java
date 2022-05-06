package pimlreports.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pimlreports.report.dto.ProductDTO;
import pimlreports.report.dto.SoldProductsDTO;
import pimlreports.report.dto.UserDTO;
import pimlreports.report.exception.EmptyCartException;
import pimlreports.report.exception.handler.RestTemplateResponseErrorHandler;
import pimlreports.report.reports.ProductReport;
import pimlreports.report.reports.ProductReportInterface;
import pimlreports.report.reports.SalesReport;
import pimlreports.report.reports.SalesReportInterface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final String PRODUCT_API_URI = "http://localhost:8081";
    private static final String PRODUCTS_RESOURCE = "/fresh-products/v1";

    private final RestTemplate restTemplate;

    @Autowired
    public ReportService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public ReportService(@Lazy RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void productBySellerIdReport(Long sellerId) {
        List<ProductDTO> sellerProducts = getAllProducts(sellerId);
        UserDTO foundUser = getUserById(sellerId);

        ProductReportInterface productReport = new ProductReport(sellerProducts);
        productReport.generateHeader(foundUser.getName());
        productReport.generateBody();
        productReport.generateFooter();
        productReport.print();
    }

    public void topSellProducts() {
        List<SoldProductsDTO> sellList = getTotalSell().stream().sorted(Comparator.comparingInt(SoldProductsDTO::getQuantity).reversed()).collect(Collectors.toList());
        SalesReportInterface salesReport = new SalesReport(sellList);
        salesReport.generateHeader();
        salesReport.generateBody();
        salesReport.generateFooter();
        salesReport.print();
    }

    public List<ProductDTO> getAllProducts(Long sellerId) {
        String resourceURI = PRODUCT_API_URI.concat(PRODUCTS_RESOURCE);
        try {
            ResponseEntity<ProductDTO[]> result = restTemplate.getForEntity(resourceURI, ProductDTO[].class);
            return Arrays.stream(Objects.requireNonNull(result.getBody())).filter(product -> product.getSellerId().equals(sellerId)).collect(Collectors.toList());

        } catch (RuntimeException ex) {

            throw new RuntimeException("Products do not exists");
        }
    }

    public UserDTO getUserById(Long userId) {
        String resourceURI = "http://localhost:8080/user/v1/".concat(String.valueOf(userId));
        try {
            return restTemplate.getForEntity(resourceURI, UserDTO.class).getBody();

        } catch (RuntimeException ex) {

            throw new RuntimeException("User not found");
        }
    }

    public List<SoldProductsDTO> getTotalSell() throws EmptyCartException {
        String resourceURI = "http://localhost:8082/api/v1/totalSell";
        try {
            ResponseEntity<SoldProductsDTO[]> result = restTemplate.getForEntity(resourceURI, SoldProductsDTO[].class);
            return Arrays.stream(Objects.requireNonNull(result.getBody())).collect(Collectors.toList());

        } catch (EmptyCartException ex) {

            throw new EmptyCartException("Empty cart");
        }
    }

}
