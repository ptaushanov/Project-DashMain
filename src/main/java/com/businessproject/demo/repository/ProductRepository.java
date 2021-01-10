package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByProductName(String productName);

    boolean existsByProductNameAndIdNot(String searchedString, String id);
}
