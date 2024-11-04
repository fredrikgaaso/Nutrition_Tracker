package com.product.shop.dtos;

import com.product.shop.model.ShopUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDTO {
    private Long id;
    private String name;
    private int wallet;
    private String password;
    private Long cartId;

    public ShopUser getUser(){
        ShopUser shopUser = new ShopUser();
        shopUser.setId(this.id);
        shopUser.setName(this.name);
        shopUser.setPassword(this.password);
        shopUser.setWallet(this.wallet);
        shopUser.setCartId(this.cartId);
        return shopUser;
    }

}
