package com.product.shop.controller;

import com.product.shop.model.shopCart;
import com.product.shop.model.shopProduct;
import com.product.shop.model.shopUser;
import com.product.shop.service.ProductShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ProductShopService productExample;

    @GetMapping("/{id}")
    public shopProduct productCommunicationSolver(@PathVariable long id) {

        log.info("Calling productExample.getText() with Sometext: {}", id);
        shopProduct shopProduct = productExample.getOneProduct(id);

        if (shopProduct == null) {
            log.error("ProductExample returned null for Sometext");
            throw new IllegalStateException("The returned Sometext is null");
        }

        log.info("Received response: id={}, name={}, price={}", shopProduct.getId(), shopProduct.getName(), shopProduct.getPrice());
        return shopProduct;
    }

    @GetMapping("/user/{id}")
    public shopUser userCommunicationSolver(@PathVariable long id) {
        shopUser shopUser = productExample.getOneUser(id);

        if (shopUser == null) {
            log.error("getOneUser returned null");
            throw new IllegalStateException("The returned user is null");
        }
        log.info("Received response: id={}, name={}, wallet={}", shopUser.getId(), shopUser.getName(), shopUser.getWallet());

        return shopUser;
    }
}
