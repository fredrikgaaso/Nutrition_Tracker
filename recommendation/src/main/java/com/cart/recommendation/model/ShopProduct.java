package com.cart.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopProduct {
    private long id;
    private String productName;
    private List<Nutrient> nutritionalInfo;
    private double calories;
    private int quantity;
    private String foodGroup;
}
