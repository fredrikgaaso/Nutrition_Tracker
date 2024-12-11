package com.product.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FoodResponseWrapper {
    private List<FoodResponse> foods;

}
