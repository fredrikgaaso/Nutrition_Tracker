package com.product.catalog.product.controller;

import com.product.catalog.product.model.Product;
import com.product.catalog.product.service.ProductApiService;
import com.product.catalog.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductApiService productApiService;


    @GetMapping("/{id}")
    public Product findOneProductById(@PathVariable Long id) {
        return productService.findOneProductById(id);
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addOneProduct(product);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Product>> fetchAllProducts() {
        productApiService.fetchAndSaveProducts(); //change to not save everytime
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/all")
    public List<Product> getAllProductsAll() {
        return productService.findAllProducts();
    }

}
