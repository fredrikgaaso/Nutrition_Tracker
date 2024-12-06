package com.product.catalog.product.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    @ElementCollection
    private List<Nutrient> nutritionalInfo;
    private String foodGroup;
    private double calories;
    private int quantity;

}

