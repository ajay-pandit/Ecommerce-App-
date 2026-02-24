package com.app.ecom.service;

import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponseDto;
import com.app.ecom.entity.Cartitem;
import com.app.ecom.entity.Order;
import com.app.ecom.entity.OrderItem;
import com.app.ecom.entity.User;
import com.app.ecom.model.OrderStstus;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public Optional<OrderResponseDto> createOrder(String userId) {
        //validate for cart item
        List<Cartitem> cartItem =cartService.getAllCartItems(userId);
        if(cartItem.isEmpty()) {
            return Optional.empty();
        }

        //validate for users
        Optional<User> userOptinal = userRepository.findById(Long.valueOf(userId));
        if(userOptinal.isEmpty()){
            return Optional.empty();
        }
        User user = userOptinal.get();
        //calculate total price
        BigDecimal totalPrice = cartItem.stream()
                .map(Cartitem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        //create order
        Order order= new Order();
        order.setUser(user);
        order.setStatus(OrderStstus.CANCELLED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItem.stream()
                .map(item->new OrderItem(
                        null,
                        item.getProduct(),
                        Math.toIntExact(item.getQuantity()),
                        item.getPrice(),
                        order
                )).toList();

        order.setItems(orderItems);
        Order savedOrder =orderRepository.save(order);
        //clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapTOOrderResponse(savedOrder));

    }

    private OrderResponseDto mapTOOrderResponse(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item-> new OrderItemDTO(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))

                        )).toList(),
                order.getCreatedAt()
        );
    }
}
