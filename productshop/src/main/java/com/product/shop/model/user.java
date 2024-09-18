package com.product.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class user {
    @Id
    @Generated
    private Long id;
    private Long money;
}
