package com.herryboro.order66.dto.order;

import com.herryboro.order66.dto.store.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class CartItem {
    private Long id;
    private Long menuId;
    private int quantity;
    private List<Option> options;
    private String jsonConvertedToString;
    private String optionsHash;
}
