package com.product.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class shopProduct {
    @Id
    private Long id;
    private String name;
    private int price;


}
