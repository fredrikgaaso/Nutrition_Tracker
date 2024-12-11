package com.cart.recommendation.dtos;

import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CartDTO {
    private long id;
    private List<ShopProduct> productsList;
    private Set<String> allergens;
    private int desiredProtein;
    private int desiredCarbs;
    private int desiredFat;

    public ShopCart getShopCart(){
        ShopCart shopCart = new ShopCart();
        shopCart.setId(this.id);
        shopCart.setProductsList(this.productsList);
        shopCart.setAllergens(this.allergens);
        shopCart.setDesiredProtein(this.desiredProtein);
        shopCart.setDesiredCarbs(this.desiredCarbs);
        shopCart.setDesiredFat(this.desiredFat);
        return shopCart;
    }
}
