package com.cart.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ShopCart {
    private Long id;
    private List<ShopProduct> productsList;
    private Set<String> allergens;
}
