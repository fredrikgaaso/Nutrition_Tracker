package com.example.cart.repo;

import com.example.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartRepo extends JpaRepository<Cart, Long> {
    Cart findOneCartById(Long id);
}
