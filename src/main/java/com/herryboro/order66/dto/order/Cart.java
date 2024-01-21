package com.herryboro.order66.dto.order;

import com.herryboro.order66.dto.store.Option;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class Cart {
    private Long id;
    private Long storeId;
    private Long userId;
    private CartItem items;
}

