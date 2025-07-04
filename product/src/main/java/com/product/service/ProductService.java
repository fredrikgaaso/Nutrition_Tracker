package com.product.service;

import com.product.model.Product;
import com.product.repos.ProductRepo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product addOneProduct(Product product) {
        productRepo.save(product);
        return product;
    }
    public Product findOneProductById(Long id) {
        return productRepo.findOneProductById(id);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();

    }
    public Product setFavorite(Long id) {
        Product product = productRepo.findOneProductById(id);
        if (product != null) {
                if (product.isFavorite()) {
                    product.setFavorite(false);
                } else {
                    product.setFavorite(true);
                }
            productRepo.save(product);
        }
        return product;
    }
}
