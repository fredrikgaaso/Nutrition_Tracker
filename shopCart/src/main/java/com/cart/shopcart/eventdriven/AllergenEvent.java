package com.cart.shopcart.eventdriven;

import lombok.Value;

import java.util.Set;

@Value
public class AllergenEvent {
    Long cartId;
    Set<String> allergens;
}
