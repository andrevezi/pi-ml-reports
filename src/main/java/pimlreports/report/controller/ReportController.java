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

    /**
     * Create a report that shows all the registered products from a specific vendor
     * @param sellerId receives the id of the seller to be checked
     * @return Will create a report called ProductsReport.pdf
     * Note: Normally would return a link to be downloaded
     */
    @GetMapping("/reports/v1/vendor")
    public String vendorReport(@RequestParam Long sellerId){
        reportService.productBySellerIdReport(sellerId);

        return "Vendor Report link";
    }

    /**
     * Create a report that shows all sales ordered by product most sold (in quantity)
     * @return Will create a report Called SalesReport.pdf
     * Note: Normally would return a link to be downloaded
     */
    @GetMapping("/reports/v1/sales")
    public String salesReport(){
        reportService.topSellProducts();

        return "Sales Report link";
    }
}
