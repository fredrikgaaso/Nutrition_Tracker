package com.product.catalog.product.repos;

import com.product.catalog.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Product findOneProductById(Long id);

}
