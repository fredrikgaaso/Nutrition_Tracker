package com.product.shop.dtos;

import com.product.shop.model.ShopCart;
import com.product.shop.model.ShopProduct;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class cartDTO {
    private Long id;
    private Long userId;
    private List<ShopProduct> products;

    public ShopCart getShopCart() {
        ShopCart shopCart = new ShopCart();
        shopCart.setId(this.id);
        shopCart.setUserId(this.userId);
        shopCart.setProductList(this.products);
        return shopCart;
    }


}
