package pimlreports.report.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pimlreports.report.dto.SoldProductsDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceTests {
    private ReportService reportService;

    @BeforeEach
    public void testSetup() {
        this.reportService = Mockito.mock(ReportService.class);
    }

    @Test
    public void shouldCreateSaleReport() {
        List<SoldProductsDTO> mockList = new ArrayList<SoldProductsDTO>();
        mockList.add(new SoldProductsDTO(1L, "Teste", 1, BigDecimal.valueOf(1), BigDecimal.valueOf(1)));

        Mockito.when(reportService.getTotalSell()).thenReturn(mockList);

        Assertions.assertDoesNotThrow(()->{
           reportService.topSellProducts();
        });

    }
}
