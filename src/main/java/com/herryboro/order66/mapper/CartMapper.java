package com.herryboro.order66.mapper;

import com.herryboro.order66.dto.order.Cart;
import com.herryboro.order66.dto.order.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {
    Cart checkHaveCart(Long clientId);

    Long insertCart(Cart cart);

    Long insertCartItem(Cart cart);

    void insertCartItemOption(Cart cart);

    CartItem checkCartItemWithoutOptions(Cart cart);

    CartItem checkCartItemWithOptions(@Param(value = "order") Cart cart, @Param(value = "optionNums") int optionNums);

    void updateCartItemQuantity(@Param(value = "cartItemId") Long cartItemId, @Param(value = "totalItemQuantity") int totalItemQuantity);
}
