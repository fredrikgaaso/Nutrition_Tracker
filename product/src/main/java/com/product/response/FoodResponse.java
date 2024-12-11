package com.product.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodResponse {
    private String foodName;
    private NutrientResponse calories;
    private List<ConstituentResponse> constituents;
    private String foodGroupId;



}