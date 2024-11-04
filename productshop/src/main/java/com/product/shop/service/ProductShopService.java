package com.product.shop.service;

import com.product.shop.clients.ShopCartClient;
import com.product.shop.clients.ShopProductClient;
import com.product.shop.clients.ShopUserClient;
import com.product.shop.dtos.ProductDTO;
import com.product.shop.dtos.cartDTO;
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
    private final ShopCartClient cartClient;
    private final ShopUserRepo userRepo;
    private final ShopProductRepo productRepo;
    private final ShopCartRepo shopCartRepo;

    public ShopProduct getOneProduct(Long shopId){
        log.info("Input product: {}", shopId);

        ProductDTO productDTO = productClient.remoteGetOneProduct(shopId);

        log.info("shopDTO received from ShopClient: {}", productDTO);


        ShopProduct response = productDTO.getProduct();

        log.info("Returning product: {}", response);

        productRepo.save(response);

        return response;
    }

    public ShopCart getOneCart(Long cartId){
        cartDTO cartDTO = cartClient.remoteGetOneCart(cartId);

        ShopCart response = cartDTO.getShopCart();

        shopCartRepo.save(response);
        log.info("cartDTO received from ShopCartClient: {}", response);
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


   /* public ShopCart createNewCart(Long userId) {
        ShopUser user = userClient.remoteGetOneUser(userId).getUser();
            ShopCart cart = new ShopCart();
            user.setCartId(cart.getId());
            cart.setUserId(user.getId());
            log.info("Cart id: {}", cart.getId());                  //trying to have cart in own service
            log.info("user id: {}", user.getId());
            userRepo.save(user);
            return shopCartRepo.save(cart);
    }*/
    public void addProductToCart(Long cartId, Long productId) {
        cartDTO cart = cartClient.remoteGetOneCart(cartId);
        ProductDTO product = productClient.remoteGetOneProduct(productId);
        log.info("Input product: {}", product.getProductName());
        log.info("Input cart: {}", cart.getId());
        cart.getProductList().add(product.getProduct());
        log.info("cart list: {}", cart.getProductList().getFirst());
        shopCartRepo.save(cart);
    }
  /*  public ShopCart getOneShopCart(Long id) {
        return shopCartRepo.findOneCartById(id);            //trying to have cart in own service
    }*/

}
