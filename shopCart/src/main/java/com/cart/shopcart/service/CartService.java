package com.cart.shopcart.service;

import com.cart.shopcart.clients.ShopProductClient;
import com.cart.shopcart.dto.ProductDTO;
import com.cart.shopcart.eventdriven.AllergenEvent;
import com.cart.shopcart.eventdriven.ProductEvent;
import com.cart.shopcart.eventdriven.ProductEventPublisher;
import com.cart.shopcart.model.ShopCart;
import com.cart.shopcart.model.ShopProduct;
import com.cart.shopcart.repos.ShopCartRepo;
import com.cart.shopcart.repos.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ShopProductClient productClient;
    private final ShopProductRepo productRepo;
    private final ShopCartRepo shopCartRepo;
    private final ProductEventPublisher productEventPublisher;


    public ShopProduct getOneProduct(Long productId) {
        ProductDTO productDTO = productClient.remoteGetOneProduct(productId);

        log.info("shopDTO received from ShopClient: {}", productDTO);

        ShopProduct response = productDTO.getProduct();

        log.info("Returning product: {}", response);

        return response;
    }

    public ShopCart getOneShopCart(Long cartId) {
        return shopCartRepo.findOneCartById(cartId);
    }

    public ShopCart createNewCart() {
            ShopCart cart = new ShopCart();
            return shopCartRepo.save(cart);
    }

    public void removeProduct(ProductEvent productEvent) {
        ShopCart cart = shopCartRepo.findOneCartById(productEvent.getCartId());
        ShopProduct product = productRepo.findById(productEvent.getProductId()).orElseThrow();
        log.info(product.getProductName() + " removed from cart");
        cart.getProductsList().remove(product);
        productEventPublisher.publishProductEvent(productEvent);
        shopCartRepo.save(cart);
    }
    public void setAllergens(Long cartId, Set<String> allergens) {
        log.info("allergens from setAllergens:" + allergens);
        ShopCart cart = shopCartRepo.findOneCartById(cartId);
        AllergenEvent allergenEvent = new AllergenEvent(cartId, allergens);
        cart.setAllergens(allergens);
        productEventPublisher.publishProductEvent(allergenEvent);
        shopCartRepo.save(cart);
    }

    public void setDesiredNutrients(Long cartId, int desiredProtein, int desiredCarbs, int desiredFat) {
        log.info("Setting desired nutrients for cartId: {}, Protein: {}, Carbs: {}, Fat: {}",
                 cartId, desiredProtein, desiredCarbs, desiredFat);
        ShopCart cart = shopCartRepo.findOneCartById(cartId);
        cart.setDesiredProtein(desiredProtein);
        cart.setDesiredCarbs(desiredCarbs);
        cart.setDesiredFat(desiredFat);
        shopCartRepo.save(cart);
    }


    public void addProductToCart(ProductEvent productEvent) {
        try {
            ShopCart cart = shopCartRepo.findOneCartById(productEvent.getCartId());
            ShopProduct product = productRepo.findById(productEvent.getProductId()).orElseGet(() -> {
                ProductDTO productDTO = productClient.remoteGetOneProduct(productEvent.getProductId());
                ShopProduct newProduct = productDTO.getProduct();
                newProduct.setQuantity(productEvent.getQuantity());
                return productRepo.save(newProduct);
            });

            log.info("Input product: {}", product.getProductName());
            log.info("Input cart: {}", cart.getId());
            log.info("cart list: {}", cart.getProductsList());

            if (cart.getProductsList().contains(product)) {
                product.setQuantity(product.getQuantity() + productEvent.getQuantity());
                productRepo.save(product);
            } else {
                cart.getProductsList().add(product);
            }
            productEventPublisher.publishProductEvent(productEvent);

            shopCartRepo.saveAndFlush(cart);
        } catch (Exception e) {
            log.error("Error adding product to cart", e);
            throw new RuntimeException("Failed to add product to cart", e);
        }
    }

    public List<ShopCart> getAllCarts() {
        return shopCartRepo.findAll();
    }
}
