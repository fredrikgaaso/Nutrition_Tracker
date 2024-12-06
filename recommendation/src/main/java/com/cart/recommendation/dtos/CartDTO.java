package com.cart.recommendation.dtos;

import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private long id;
    private List<ShopProduct> productsList;

    public ShopCart getShopCart(){
        ShopCart shopCart = new ShopCart();
        shopCart.setId(this.id);
        shopCart.setProductsList(this.productsList);
        return shopCart;
    }
}
