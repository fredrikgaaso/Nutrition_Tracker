package com.product.shop.dtos;

import com.product.shop.model.ShopProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private int price;
    private long id;

    public ShopProduct getProduct() {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(this.id);
        shopProduct.setName(this.name);
        shopProduct.setPrice(this.price);

        return shopProduct;
    }

}
