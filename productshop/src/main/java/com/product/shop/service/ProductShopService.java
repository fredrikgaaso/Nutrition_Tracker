package com.product.shop.service;

import com.product.shop.clients.ShopProductClient;
import com.product.shop.clients.ShopUserClient;
import com.product.shop.dtos.ProductDTO;
import com.product.shop.dtos.userDTO;
import com.product.shop.model.ShopCart;
import com.product.shop.model.ShopProduct;
import com.product.shop.model.ShopUser;
import com.product.shop.repos.ShopCartRepo;
import com.product.shop.repos.ShopProductRepo;
import com.product.shop.repos.ShopUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductShopService {
    private final ShopProductClient productClient;
    private final ShopUserClient userClient;
    private final ShopUserRepo userRepo;
    private final ShopProductRepo productRepo;

    public ShopProduct getOneProduct(Long shopId){
        log.info("Input product: {}", shopId);

        ProductDTO productDTO = productClient.remoteGetOneProduct(shopId);

        log.info("shopDTO received from ShopClient: {}", productDTO);


        ShopProduct response = productDTO.getProduct();

        log.info("Returning product: {}", response);

        productRepo.save(response);

        return response;
    }

    public ShopUser getOneUser(Long userId){
        log.info("Input user: {}", userId);

        userDTO userDTO = userClient.remoteGetOneUser(userId);
        log.info("userDTO received from userClient: {}", userDTO);

        ShopUser response = userDTO.getUser();

        log.info("Returning user: {}", response);

        userRepo.save(response);

        return response;
    }
    private final ShopCartRepo cartRepo;

    public ShopCart createNewCart(Long userId) {
        ShopUser user = userClient.remoteGetOneUser(userId).getUser();
        if (user == null) {
            log.warn("User got: " + user.getName());
        }
        ShopCart cart = new ShopCart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }
    public void addProductToCart(Long cartId, Long productId) {
        ShopCart cart = cartRepo.findOneCartById(cartId);
        ProductDTO product = productClient.remoteGetOneProduct(productId);
        if (isProductInCart(cart, productId)) {
            return;
        }
        cart.getProductList().add(product.getProduct());
        cartRepo.save(cart);
    }
    public ShopCart getOneShopCart(Long id) {
        return cartRepo.findOneCartById(id);
    }

    public boolean isProductInCart(ShopCart cart, Long productId) {
        for (ShopProduct product : cart.getProductList()) {
            if (product.getId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

}
