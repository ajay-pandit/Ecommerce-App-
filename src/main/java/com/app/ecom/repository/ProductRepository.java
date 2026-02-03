package com.app.ecom.repository;

import com.app.ecom.dto.ProductResponce;
import com.app.ecom.entity.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT P FROM Product P WHERE P.active=true AND LOWER(P.name) LIKE LOWER(CONCAT('%',:keyword ,'%'))")
    @Nullable List<Product> searchProducts( @Param("keyword") String keyword);
}
