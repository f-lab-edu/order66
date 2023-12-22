package com.herryboro.order66.dto.order;

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
    Long cartItemId;
    int purchaseQuantity;
    List<Option> options;
}

