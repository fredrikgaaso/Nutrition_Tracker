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
    @ManyToMany
    private List<ShopProduct> productsList;
    @ElementCollection
    private Set<String> Allergens;
}