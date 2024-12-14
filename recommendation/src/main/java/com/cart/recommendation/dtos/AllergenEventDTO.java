package com.cart.recommendation.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

@Value
@ToString
@Getter
@Setter
public class AllergenEventDTO {
    Long cartId;
    Set<String> allergens;
}
