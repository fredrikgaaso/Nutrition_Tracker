package com.product.shop.dtos;

import com.product.shop.model.Nutrient;
import com.product.shop.model.ShopProduct;
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

    public ShopProduct getProduct() {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(this.id);
        shopProduct.setProductName(this.productName);
        shopProduct.setCalories(this.calories);
        shopProduct.setNutritionalInfo(this.nutritionalInfo);

        return shopProduct;
    }

}
