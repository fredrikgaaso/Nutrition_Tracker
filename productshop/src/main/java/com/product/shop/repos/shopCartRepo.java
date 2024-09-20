package com.product.shop.repos;

import com.product.shop.model.shopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shopCartRepo extends JpaRepository<shopCart,Long>  {

    shopCart findOneCartById(Long userId);
}
