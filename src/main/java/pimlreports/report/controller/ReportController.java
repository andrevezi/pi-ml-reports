package pimlreports.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pimlreports.report.entity.Product;
import pimlreports.report.reports.ProductReport;
import pimlreports.report.reports.Report;
import pimlreports.report.service.ReportService;

import java.util.List;

@RestController
@RequestMapping
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<?> firstReport(@RequestParam Long sellerId){
        List<Product> products = reportService.getAllProducts(sellerId);
        Report testReport = new ProductReport(products);
        testReport.generateHeader();
        testReport.generateBody();
        testReport.print();
        return ResponseEntity.ok(products);
    }
}
