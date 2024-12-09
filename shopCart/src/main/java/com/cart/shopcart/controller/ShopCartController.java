package com.cart.shopcart.controller;

import com.cart.shopcart.eventdriven.ProductEvent;
import com.cart.shopcart.model.ShopCart;
import com.cart.shopcart.model.ShopProduct;
import com.cart.shopcart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShopCartController {
    private final CartService cartService;

    @GetMapping("/product/{productId}")
    public ShopProduct findOneProductById(@PathVariable long productId) {

        log.info("Calling productExample.getText() with Sometext: {}", productId);
        ShopProduct shopProduct = cartService.getOneProduct(productId);

        if (shopProduct == null) {
            log.error("ProductExample returned null for Sometext");
            throw new IllegalStateException("The returned Sometext is null");
        }

        log.info("Received response: id={}, name={}, nutrition={}, calories={}", shopProduct.getId(), shopProduct.getProductName(), shopProduct.getNutritionalInfo(), shopProduct.getCalories());
        return shopProduct;
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestBody ProductEvent productEvent) {
       Long cartId = productEvent.getCartId();
       Long productId = productEvent.getProductId();
       int quantity = productEvent.getQuantity();
       if (cartId == null || productId == null || quantity == 0) {
           log.error("Invalid input: cartId={}, productId={}, quantity={}", cartId, productId, quantity);
           throw new IllegalArgumentException("Invalid input");
         }
        cartService.addProductToCart(productEvent);
    }

    @PostMapping("/remove")
    public void removeProduct(@RequestBody ProductEvent productEvent) {
        cartService.removeProduct(productEvent);
    }

    @PostMapping("/setAllergens/{cartId}")
    public void setAllergens(@PathVariable Long cartId, @RequestBody Set<String> allergens) {
        cartService.setAllergens(cartId, allergens);
    }

    @GetMapping("/{cartId}")
    public ShopCart findOneCartById(@PathVariable Long cartId) {
        return cartService.getOneShopCart(cartId);
    }

    @PostMapping("/create")
    public ShopCart createNewCart() {
        return cartService.createNewCart();
    }
   /* @PostMapping("/add/{cartId}/product/{productId}/{quantity}")
    public void addProductToCart(@PathVariable Long cartId,@PathVariable Long productId, @PathVariable int quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
    }*/

    @GetMapping("/all")
    public List<ShopCart> getAllCarts() {
        return cartService.getAllCarts();
    }
}
