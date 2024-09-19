package com.product.shop.repos;

import com.product.shop.model.shopProduct;
import com.product.shop.model.user;

public interface ProductShopInterface {
    shopProduct getOneProduct(Long shopId);
}
