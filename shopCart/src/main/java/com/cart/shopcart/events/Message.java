package com.cart.shopcart.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private long cartId;
    private String productId;
    private int quantity;
}
