package com.product.catalog.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodResponse {
    private String foodName;
    private NutrientResponse calories;
    private List<ConstituentResponse> constituents;
}