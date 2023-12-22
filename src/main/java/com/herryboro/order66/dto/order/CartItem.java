package com.herryboro.order66.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItem {
    private Long id;
    private Long cartId;
    private Long menuId;
    private int itemQuantity;
}
