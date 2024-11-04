package com.cart.shoppingcart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class cart {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
}
