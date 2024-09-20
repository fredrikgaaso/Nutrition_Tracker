package com.product.shop.repos;

import com.product.shop.model.shopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface shopProductRepo extends JpaRepository<shopProduct,Long> {
}
