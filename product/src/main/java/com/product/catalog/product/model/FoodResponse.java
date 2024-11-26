package com.product.catalog.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class FoodResponse {
    @Id
    private long id;
    private String foodName;
    @ManyToOne
    private NutrientResponse calories;
    @OneToMany
    private List<ConstituentResponse> constituents;
}