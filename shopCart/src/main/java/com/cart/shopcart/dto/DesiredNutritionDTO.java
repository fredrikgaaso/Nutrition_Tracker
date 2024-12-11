package com.cart.shopcart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesiredNutritionDTO {
    private int desiredProtein;
    private int desiredCarbs;
    private int desiredFat;

    public DesiredNutritionDTO(int desiredProtein, int desiredCarbs, int desiredFat) {
        this.desiredProtein = desiredProtein;
        this.desiredCarbs = desiredCarbs;
        this.desiredFat = desiredFat;
    }
}
