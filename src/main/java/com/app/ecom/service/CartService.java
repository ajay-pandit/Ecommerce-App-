package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.entity.Cartitem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest requst) {
        Optional<Product> productOpt = productRepository.findById(requst.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product=productOpt.get();

        if(product.getStockQuantity()< requst.getQuantity()){
            return false;
        }

        Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
        User user=userOpt.get();
        if(userOpt.isEmpty()){
            return false;
        }

        Cartitem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);

        if(existingCartItem!=null){
            //update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ requst.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(existingCartItem.getPrice()));
            cartItemRepository.save(existingCartItem);
        }
        else{
            //create new
            Cartitem cartItem= new Cartitem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(Long.valueOf(requst.getQuantity()));
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(requst.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;

    }

    public boolean deleteProductFromCart(String userId, Long productId) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        Optional<Product> product = productRepository.findById(productId);

        if(user.isPresent() && product.isPresent()){
            cartItemRepository.deleteByUserAndProduct(user,product);
            return true;
        }
        return false;
    }

    public List<Cartitem> getAllCartItems(String userId) {
        System.out.println("working 1");
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        List<Cartitem> AllCartItems = new ArrayList<Cartitem>() ;
        if(user.isPresent()){
            AllCartItems=cartItemRepository.getAllCartItemsByUser(user);
        }
        return AllCartItems;


    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(user->
                cartItemRepository.deleteByUser(user)
        );
    }
}
