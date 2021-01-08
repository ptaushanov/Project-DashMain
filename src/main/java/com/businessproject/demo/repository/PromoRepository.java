package com.businessproject.demo.repository;

import com.businessproject.demo.model.PromoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


public interface PromoRepository extends MongoRepository<PromoEvent, String> {
    List<PromoEvent> findAllByManagedById(String salesRepId);

    boolean existsByProductIdAndManagedById(String productId, String managedById);

    boolean existsByProductId(String productId);

    void deleteByProductId(String id);

    @Query(value = "{ 'managedById' : ?0, 'productId' : ?1, 'startDate' : { $lte : ?2 }, 'endDate' : { $gte : ?2 }} ")
    Optional<PromoEvent> findPromoEvent(String managedById, String productId, ZonedDateTime comparableDateTime);
}
