package com.cart.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopCart {
    private Long id;
    private List<ShopProduct> productsList;
}
