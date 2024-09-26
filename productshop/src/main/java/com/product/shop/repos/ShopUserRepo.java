package com.product.shop.repos;

import com.product.shop.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopUserRepo extends JpaRepository<ShopUser,Long> {
}
