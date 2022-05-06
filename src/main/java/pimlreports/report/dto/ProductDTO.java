package pimlreports.report.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double minimumTemperature;
    private Double size;
    private BigDecimal price;
    private Long sellerId;
    private String category;
}
