package com.cart.shopcart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class ShopCart{
    @Id
    @GeneratedValue
    private Long id;
    private String cartName;
    @ManyToMany
    private List<ShopProduct> productsList;
    @ElementCollection
    private Set<String> Allergens;
    private int desiredProtein;
    private int desiredCarbs;
    private int desiredFat;
}