package com.product.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodGroupResponse {
    private String foodGroupId;
    private String name;
    private String parentId;

}
