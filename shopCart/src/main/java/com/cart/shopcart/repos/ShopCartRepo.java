package com.cart.shopcart.repos;

import com.cart.shopcart.model.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartRepo extends JpaRepository<ShopCart,Long> {
    ShopCart findOneCartById(Long userId);

}
