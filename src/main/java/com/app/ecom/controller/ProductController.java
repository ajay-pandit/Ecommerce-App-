package com.app.ecom.controller;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponce;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponce> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponce>(productService.createProduct(productRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,@RequestBody ProductRequest productRequest){
        return new ResponseEntity<String>(productService.updateProduct(id,productRequest),HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponce>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponce> deleteProduct(@PathVariable Long id){
        boolean deleted=productService.deleteProduct(id);
        return deleted==true?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponce>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProdcts(keyword));
    }
}
