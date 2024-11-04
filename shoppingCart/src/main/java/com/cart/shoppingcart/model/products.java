package com.cart.shoppingcart.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class products {
    @Id
    private Long id;
    private String productName;
    @ElementCollection
    private List<Nutrient> nutritionalInfo;
    private double calories;
}
