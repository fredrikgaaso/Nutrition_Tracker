package com.cart.shopcart.service;

import com.cart.shopcart.clients.ShopProductClient;
import com.cart.shopcart.dto.ProductDTO;
import com.cart.shopcart.model.ShopCart;
import com.cart.shopcart.model.ShopProduct;
import com.cart.shopcart.repos.ShopCartRepo;
import com.cart.shopcart.repos.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ShopProductClient productClient;
    private final ShopProductRepo productRepo;
    private final ShopCartRepo shopCartRepo;


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


    public void addProductToCart(Long cartId, Long productId, int quantity) {
        ShopCart cart = shopCartRepo.findOneCartById(cartId);
        ShopProduct product = productRepo.findById(productId).orElseGet(() -> {
            ProductDTO productDTO = productClient.remoteGetOneProduct(productId);
            ShopProduct newProduct = productDTO.getProduct();
            newProduct.setQuantity(quantity);
            return productRepo.save(newProduct);
        });

        log.info("Input product: {}", product.getProductName());
        log.info("Input cart: {}", cart.getId());
        log.info("cart list: {}", cart.getProductsList());

        if (cart.getProductsList().contains(product)) {
            product.setQuantity(product.getQuantity() + quantity);
            productRepo.save(product);
        }
        else {
            cart.getProductsList().add(product);
        }
        shopCartRepo.saveAndFlush(cart);
    }

    public List<ShopCart> getAllCarts() {
        return shopCartRepo.findAll();
    }
}
