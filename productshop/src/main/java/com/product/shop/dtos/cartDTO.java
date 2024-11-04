package com.product.shop.dtos;

import com.product.shop.model.ShopCart;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class cartDTO {
    private Long id;
    private Long userId;

    public ShopCart getShopCart() {
        ShopCart shopCart = new ShopCart();
        shopCart.setId(this.id);
        shopCart.setUserId(this.userId);
        return shopCart;
    }


}
