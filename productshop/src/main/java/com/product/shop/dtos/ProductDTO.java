package com.product.shop.dtos;

import com.product.shop.model.Nutrient;
import com.product.shop.model.ShopProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private double calories;
    private long id;
    private List<Nutrient> nutrientList;

    public ShopProduct getProduct() {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(this.id);
        shopProduct.setProductName(this.name);
        shopProduct.setCalories(this.calories);
        shopProduct.setNutritionalInfo(this.nutrientList);

        return shopProduct;
    }

}
