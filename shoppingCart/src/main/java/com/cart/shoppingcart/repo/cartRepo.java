package com.cart.shoppingcart.repo;

import com.cart.shoppingcart.model.cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface cartRepo extends JpaRepository<cart,Long> {

}
