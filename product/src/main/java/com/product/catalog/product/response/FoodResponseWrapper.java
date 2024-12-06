package com.product.catalog.product.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodResponseWrapper {
    private List<FoodResponse> foods;
}
