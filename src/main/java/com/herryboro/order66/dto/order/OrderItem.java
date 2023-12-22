package com.herryboro.order66.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrderItem {
    private Long orderItemId;
    private String orderMenu;
    private int orderQuantity;
    private int orderPrice;
    private String orderOption;
}
