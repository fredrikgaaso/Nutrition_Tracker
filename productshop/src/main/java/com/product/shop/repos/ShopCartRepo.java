package com.product.shop.repos;

import com.product.shop.model.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartRepo extends JpaRepository<ShopCart,Long>  {

    ShopCart findOneCartById(Long userId);
    ShopCart findUserCartByUserId(Long userId);
}
