package com.businessproject.demo.repository;

import com.businessproject.demo.model.PromoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoRepository extends MongoRepository<PromoEvent, String> {
    boolean existsByProductId(String id);

    void deleteByProductId(String id);
}
