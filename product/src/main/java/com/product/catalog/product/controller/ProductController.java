package com.product.catalog.product.controller;

import com.product.catalog.product.model.Product;
import com.product.catalog.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findOneProductById(id);
    }
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addOneProduct(product);
    }


}
