package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponce;
import com.app.ecom.entity.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponce createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapToProductFromRequest(product,productRequest);
        Product savedProduct=productRepository.save(product);
        return mapToProductResponce(savedProduct);
    }

    public @Nullable String updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct->{
            System.out.println(existingProduct);
            mapToProductFromRequest(existingProduct,productRequest);
            productRepository.save(existingProduct);
            return "Product Updated";
        }).orElse( "Please enter a valid product");
    }

//    public @Nullable ProductResponce GetProduct(Long id) {
//
//    }

    private ProductResponce mapToProductResponce(Product savedProduct) {
        ProductResponce responce = new ProductResponce();
        responce.setId(savedProduct.getId());
        responce.setName(savedProduct.getName());
        responce.setCategory(savedProduct.getCategory());
        responce.setPrice(savedProduct.getPrice());
        responce.setDescription(savedProduct.getDescription());
        responce.setActive(savedProduct.getActive());
        responce.setImageUrl(savedProduct.getImageUrl());
        responce.setStockQuantity(savedProduct.getStockQuantity());
        return responce;
    }

    private void mapToProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }


    public List<ProductResponce> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponce)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);

    }

    public @Nullable List<ProductResponce> searchProdcts(String keyword) {

        System.out.println("SERVICE keyword = " + keyword);
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::mapToProductResponce)
                .collect(Collectors.toList());
    }
}
