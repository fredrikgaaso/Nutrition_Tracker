package com.cart.recommendation.repos;

import com.cart.recommendation.model.RecommendationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepo extends JpaRepository<RecommendationData, Long> {
    RecommendationData findByShopCartId(Long shopCartId);
}
