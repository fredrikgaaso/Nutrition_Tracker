package com.product.catalog.product.repos;

import com.product.catalog.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepo extends JpaRepository<Product,Long> {
}
