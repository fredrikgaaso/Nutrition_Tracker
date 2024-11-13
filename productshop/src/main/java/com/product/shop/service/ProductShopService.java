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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductShopService {
    private final ShopProductClient productClient;
    private final ShopUserClient userClient;
    private final ShopUserRepo userRepo;
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

    public ShopUser getOneUser(Long userId){
        log.info("Input user: {}", userId);

        userDTO userDTO = userClient.remoteGetOneUser(userId);
        log.info("userDTO received from userClient: {}", userDTO);

        ShopUser response = userDTO.getUser();

        log.info("Returning user: {}", response);

        userRepo.save(response);

        return response;
    }

    public ShopCart createNewCart(Long userId) {
        if (shopCartRepo.findUserCartByUserId(userId) == null) {
            userDTO user = userClient.remoteGetOneUser(userId);
            ShopCart cart = new ShopCart();
            cart.setUserId(user.getId());
            log.info("Cart id: {}", cart.getId());
            log.info("user id: {}", user.getId());
            userRepo.save(user.getUser());
            return shopCartRepo.save(cart);
        }
        log.info("This user already has a cart");
        return null;

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

    public ShopCart getOneShopCart(Long id) {
        return shopCartRepo.findOneCartById(id);
    }

    public List<ShopProduct> getAllProducts(){
       List<ShopProduct> shopProducts = productRepo.findAll();
       for (int i =0; i<shopProducts.size(); i++) {
           return shopProducts;
       }
       log.info("No product found");
       return null;

    }
}
