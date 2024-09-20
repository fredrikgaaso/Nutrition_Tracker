package com.product.shop.model;

import com.product.shop.dtos.productDTO;
import com.product.shop.dtos.userDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class shopCart {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private shopUser user;
    @OneToMany
    private List<shopProduct> productList;
}
