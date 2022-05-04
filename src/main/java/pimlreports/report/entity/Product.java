package pimlreports.report.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double minimumTemperature;
    private Double size;
    private BigDecimal price;
    private Long sellerId;
    private String category;
}
