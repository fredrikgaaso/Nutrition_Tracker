package com.cart.shopcart.repos;

import com.cart.shopcart.model.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopProductRepo extends JpaRepository<ShopProduct,Long> {
}
