package com.example.cart.service;

import com.example.cart.model.Cart;
import com.example.cart.repo.cartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class cartService {
    private final cartRepo cartRepo;


    public Cart findOneCartById(Long id) {
        return cartRepo.findOneCartById(id);
    }
}
