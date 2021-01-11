package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Check if a product with the given name exists in the database
    boolean existsByProductName(String productName);

    // Check if a product with the given name but not with the given id
    // exists in the database
    boolean existsByProductNameAndIdNot(String productName, String id);
}
