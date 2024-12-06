package com.product.catalog.product.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodGroupWrapper {
    private List<FoodGroupResponse> foodGroups;

}
