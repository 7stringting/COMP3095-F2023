package ca.gbc.productservice.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;

}