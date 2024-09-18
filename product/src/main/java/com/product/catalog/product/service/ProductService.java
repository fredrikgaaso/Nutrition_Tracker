package com.product.catalog.product.service;

import com.product.catalog.product.model.Product;
import com.product.catalog.product.repos.ProductRepo;
import lombok.*;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public Product addOneProduct(Product product) {
        productRepo.save(product);
        return product;
    }
    public Product findOneProductById(Long id) {
        return productRepo.findOneProductById(id);
    }
}