package com.app.ecom.repository;

import com.app.ecom.entity.Cartitem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<Cartitem,Long> {

    Cartitem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(Optional<User> user, Optional<Product> product);
}
