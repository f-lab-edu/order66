package com.herryboro.order66.controller;

import com.herryboro.order66.dto.order.Cart;
import com.herryboro.order66.dto.order.Order;
import com.herryboro.order66.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService cartService;

    @PostMapping(value = "/carts")
    public ResponseEntity<String> registerCart(@RequestBody Cart cart) {
        cartService.registerCart(cart);
        return ResponseEntity.ok("카트 추가");
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<String> registerOrder(@RequestBody Order order) {
        cartService.registerOrder(order);
        return ResponseEntity.ok("주문 추가");
    }

}
