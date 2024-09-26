package com.product.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class ShopCart {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private ShopUser user;
    @OneToMany
    private List<ShopProduct> productList;
}
