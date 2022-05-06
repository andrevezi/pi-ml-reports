package pimlreports.report.controller;


import org.springframework.web.bind.annotation.*;

import pimlreports.report.service.ReportService;


@RestController
@RequestMapping
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports/v1/vendor")
    public String vendorReport(@RequestParam Long sellerId){
        reportService.productBySellerIdReport(sellerId);

        return "Vendor Report link";
    }
    @GetMapping("/reports/v1/sales")
    public String salesReport(){
        reportService.topSellProducts();

        return "Sales Report link";
    }
}
