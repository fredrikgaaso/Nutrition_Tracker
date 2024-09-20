package com.product.shop.dtos;

import com.product.shop.model.shopProduct;
import com.product.shop.model.shopUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDTO {
    private Long id;
    private String name;
    private int wallet;

    public shopUser getUser(){
        shopUser shopUser = new shopUser();
        shopUser.setId(this.id);
        shopUser.setName(this.name);
        shopUser.setWallet(this.wallet);
        return shopUser;
    }

}
