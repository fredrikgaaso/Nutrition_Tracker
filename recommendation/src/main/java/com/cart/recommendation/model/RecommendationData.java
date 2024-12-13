package com.cart.recommendation.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Literal;

import java.util.List;

@Getter
@Setter
@Entity
public class RecommendationData {
    @Id
    private long shopCartId;
    @ElementCollection
    private List<String> allergens;
    @ElementCollection
    private List<String> nutritionalValues;
    @ElementCollection
    private List<String> recommendedProducts;
}
