package com.product.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class shopUser {
    @Id
    private Long id;
    private String name;
    private int wallet;
}
