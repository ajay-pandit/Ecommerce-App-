package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.entity.Cartitem;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest requst
    ){
        boolean sucuess=cartService.addToCart(userId,requst);
        if(!sucuess){
            return ResponseEntity.badRequest().body("Invalid request");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<String> deleteProductFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean sucess=cartService.deleteProductFromCart(userId,productId);
        if(!sucess){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<List<Cartitem>> getAllCartItems(
            @RequestHeader("X-User-Header") String userId
    ){
        List<Cartitem> allCartItems= cartService.getAllCartItems(userId);
        return ResponseEntity.ok(allCartItems);

    }
}
