package com.product.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShopUser {
    @Id
    private Long id;
    private String name;
    private int wallet;
}
