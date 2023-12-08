package com.herryboro.order66.service;

import com.herryboro.order66.dto.cart.Cart;
import com.herryboro.order66.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    public void addCart(Cart cart) {
//        cartMapper.insert
    }
}
