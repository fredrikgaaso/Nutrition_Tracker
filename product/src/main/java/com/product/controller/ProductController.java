package com.product.controller;

import com.product.model.Product;
import com.product.service.ProductApiService;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductApiService productApiService;
    private boolean productsFetched = false;

    @Autowired
    public ProductController(ProductService productService, ProductApiService productApiService) {
        this.productService = productService;
        this.productApiService = productApiService;
    }


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
      if (!productsFetched) {
          productApiService.fetchAndSaveProducts();
          productsFetched = true;
      }
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/all")
    public List<Product> getAllProductsAll() {
        return productService.findAllProducts();
    }

    @PostMapping("/setFavorite/{id}")
    public ResponseEntity<Product> setFavorite(@PathVariable Long id) {
        if (id != null && productService.findOneProductById(id) != null) {
            log.info("Setting favorite for product with id: {}", id);
        Product product = productService.setFavorite(id);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
