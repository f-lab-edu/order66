package com.herryboro.order66.controller;

import com.herryboro.order66.dto.cart.Cart;
import com.herryboro.order66.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/carts")
    public ResponseEntity<String> addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return ResponseEntity.ok("카트 추가");
    }
}
