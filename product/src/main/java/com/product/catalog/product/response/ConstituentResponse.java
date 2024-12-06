package com.product.catalog.product.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ConstituentResponse {
    private String nutrientId;
    private Double quantity;
    private String unit;
}
