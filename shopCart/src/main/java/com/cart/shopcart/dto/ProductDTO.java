package com.cart.shopcart.dto;

import com.cart.shopcart.model.Nutrient;
import com.cart.shopcart.model.ShopProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private long id;
    private String productName;
    private List<Nutrient> nutritionalInfo;
    private double calories;
    private String foodGroup;

    public ShopProduct getProduct() {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(this.id);
        shopProduct.setProductName(this.productName);
        shopProduct.setCalories(this.calories);
        shopProduct.setNutritionalInfo(this.nutritionalInfo);
        shopProduct.setFoodGroup(this.foodGroup);

        return shopProduct;
    }

}