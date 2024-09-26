package com.product.shop.repos;

import com.product.shop.model.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepo extends JpaRepository<ShopProduct,Long> {
}
