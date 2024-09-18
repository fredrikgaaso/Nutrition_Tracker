package com.product.shop.dtos;

import com.product.shop.model.shopProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class shopDTO {
    private String name;
    private int price;
    private long id;

    public shopProduct returnText() {
        shopProduct shopProduct = new shopProduct();
        shopProduct.setId(this.id);
        shopProduct.setName(this.name);
        shopProduct.setPrice(this.price);

        return shopProduct;
    }

}
