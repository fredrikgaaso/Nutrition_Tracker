package com.product.catalog.product.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NutrientResponse {
    private Double quantity;
    private String unit;
}

