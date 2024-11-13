package com.cart.shopcart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ShopCart{

    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<ShopProduct> productsList;
}