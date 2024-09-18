package com.product.shop.controller;

import com.product.shop.model.shopProduct;
import com.product.shop.service.ProductExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ProductExample productExample;

    @GetMapping("/{id}")
    public shopProduct communicationSolver(@PathVariable long id) {

        log.info("Calling productExample.getText() with Sometext: {}", id);
        shopProduct st = productExample.getOneProduct(id);

        if (st == null) {
            log.error("ProductExample returned null for Sometext");
            throw new IllegalStateException("The returned Sometext is null");
        }

        log.info("Received response: id={}, name={}, price={}", st.getId(), st.getName(), st.getPrice());
        return st;
    }
}
