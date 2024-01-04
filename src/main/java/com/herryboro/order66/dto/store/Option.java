package com.herryboro.order66.dto.store;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Option {
    private Long id;
    private Long menuId;
    private String optionName;
    private int price;
}
