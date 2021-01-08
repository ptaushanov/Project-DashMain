package com.businessproject.demo.repository;

import com.businessproject.demo.model.PromoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PromoRepository extends MongoRepository<PromoEvent, String> {
    List<PromoEvent> findAllByManagedById(String salesRepId);

    boolean existsByProductIdAndManagedById(String productId, String manageId);

    boolean existsByProductId(String productId);

    void deleteByProductId(String id);

    Optional<PromoEvent> findByProductIdAndManagedById(String productId, String manageId);
}
