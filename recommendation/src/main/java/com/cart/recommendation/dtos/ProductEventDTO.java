package com.cart.recommendation.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Getter
@Setter
public class ProductEventDTO {
    Long cartId;
    Long productId;
    int quantity;
}
