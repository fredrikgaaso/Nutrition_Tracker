package com.product.catalog.product.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodGroupResponse {
    private String foodGroupId;
    private String name;
    private String parentId;
}
