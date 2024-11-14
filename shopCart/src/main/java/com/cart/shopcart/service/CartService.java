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


    public ShopProduct getOneProduct(Long productId){

        ProductDTO productDTO = productClient.remoteGetOneProduct(productId);

        log.info("shopDTO received from ShopClient: {}", productDTO);


        ShopProduct response = productDTO.getProduct();

        log.info("Returning product: {}", response);

        productRepo.save(response);

        return response;
    }

    public ShopCart getOneShopCart(Long cartId) {
        return shopCartRepo.findOneCartById(cartId);
    }

    public ShopCart createNewCart() {
            ShopCart cart = new ShopCart();
            return shopCartRepo.save(cart);
    }


    public void addProductToCart(Long cartId, Long productId) {
        ShopCart cart = shopCartRepo.findOneCartById(cartId);
        ProductDTO product = productClient.remoteGetOneProduct(productId);
        log.info("Input product: {}", product.getProductName());
        log.info("Input cart: {}", cart.getId());
        cart.getProductsList().add(product.getProduct());
        log.info("cart list: {}", cart.getProductsList());
        shopCartRepo.saveAndFlush(cart);
    }

    public List<ShopCart> getAllCarts() {
        return shopCartRepo.findAll();
    }
}
