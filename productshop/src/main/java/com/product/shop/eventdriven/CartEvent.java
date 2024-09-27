package com.product.shop.eventdriven;

import com.product.shop.model.ShopProduct;
import com.product.shop.model.ShopUser;
import lombok.Value;
import org.springframework.context.event.EventListener;

import java.util.List;

@Value
public class CartEvent {
    Long productId;
    Long userId;

    Long cartId;
    List<ShopProduct> productList;
    ShopUser user;

}
