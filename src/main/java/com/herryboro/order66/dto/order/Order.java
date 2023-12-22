package com.herryboro.order66.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class Order {
    private Long orderId;
    private Long clinetId;
    private Long storeId;
    private String orderTime;
    private String clientRequset;
    private String orderStatus;
    private int totalPrice;
    private String expectedPickupTime;
    private List<OrderItem> orderItemList;
}
