package com.app.ecom.dto;

import com.app.ecom.model.OrderStstus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStstus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
