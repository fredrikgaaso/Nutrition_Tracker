package com.cart.shoppingcart.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Nutrient {
    private String nutrientName;
    private Double nutrientValue;
}
