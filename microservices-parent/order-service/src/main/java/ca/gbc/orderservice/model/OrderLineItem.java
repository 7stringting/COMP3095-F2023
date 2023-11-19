package ca.gbc.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "t_order_line_item")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
    }

    public void setQuantity(Integer quantity) {
    }

    public void setSkuCode(String skuCode) {
    }
}
