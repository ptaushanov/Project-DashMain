package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.PromoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


public interface PromoRepository extends MongoRepository<PromoEvent, String> {
    // Finds all promotion events that are managed by the same sales representative
    List<PromoEvent> findAllByManagedById(String salesRepId);

    // Checks if a promotion event with given product id and sales representative
    // id exists in the database
    boolean existsByProductIdAndManagedById(String productId, String managedById);

    // Checks if a promotion event with given product id exists in the database
    boolean existsByProductId(String productId);

    // Deletes all promotion events that are for the same product
    void deleteByProductId(String id);

    // Finds a promotion event that match the query criteria:
    // ⚬ Sales representative id must match ("managedById")
    // ⚬ Product id must match
    // ⚬ Compared date must be between a start date and end date
    @Query(value = "{ 'managedById' : ?0, 'productId' : ?1, 'startDate' : { $lte : ?2 }, 'endDate' : { $gte : ?2 }} ")
    Optional<PromoEvent> findPromoEvent(String managedById, String productId, ZonedDateTime comparableDateTime);
}
