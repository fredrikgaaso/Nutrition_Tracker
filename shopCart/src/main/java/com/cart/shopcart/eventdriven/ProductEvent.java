package com.cart.shopcart.eventdriven;


import lombok.Value;

@Value
public class ProductEvent {
    Long cartId;
    Long productId;
    int quantity;
}
