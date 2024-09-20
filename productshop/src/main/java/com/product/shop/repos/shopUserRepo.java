package com.product.shop.repos;

import com.product.shop.model.shopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface shopUserRepo extends JpaRepository<shopUser,Long> {
}
