package com.herryboro.order66.dto.cart;

import com.herryboro.order66.dto.store.Option;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class Cart {
    Long id;
    Long storeId;
    Long clientId;
    Long menuId;
    int purchaseQuantity;
    int totalPrice;
    List<Option> options;
}
