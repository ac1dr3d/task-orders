package com.task.orders.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {

    @NotBlank
    private Long user_id;

    @NotBlank
    private String product;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private BigDecimal price;

}