package pimlreports.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pimlreports.report.entity.Product;
import pimlreports.report.exception.handler.RestTemplateResponseErrorHandler;

import java.util.Arrays;
import java.util.List;
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

    public List<Product> getAllProducts(Long sellerId) {
        String resourceURI = PRODUCT_API_URI.concat(PRODUCTS_RESOURCE);
        try {
            ResponseEntity<Product[]> result = restTemplate.getForEntity(resourceURI, Product[].class);
            return Arrays.stream(result.getBody()).filter(product -> product.getSellerId().equals(sellerId)).collect(Collectors.toList());

        } catch (RuntimeException ex) {

            throw new RuntimeException("Products do not exists");
        }
    }

}
