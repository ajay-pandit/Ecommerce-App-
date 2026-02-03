package com.app.ecom.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponce {
    private Long id;
    private String description;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;

}
