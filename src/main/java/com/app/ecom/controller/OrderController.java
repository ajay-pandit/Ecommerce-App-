package com.app.ecom.controller;

import com.app.ecom.dto.OrderResponseDto;
import com.app.ecom.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestHeader("X-User-ID") String userId
    ){
       return orderService.createOrder(userId)
               .map(orderResponseDto -> new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED)).orElseGet(
                       ()->ResponseEntity.badRequest().build()
               );
    }
}
