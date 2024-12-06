package com.product.catalog.product.response;

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
    private String foodGroupId;
}