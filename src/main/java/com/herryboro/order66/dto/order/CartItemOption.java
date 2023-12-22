package com.herryboro.order66.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CartItemOption {
    private Long id;
    private Long cartItemId;
    private Long optionId;
    private Integer menuOptionPrice;
}
