package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.SalesRepresentative;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesRepRepository extends MongoRepository<SalesRepresentative, String> {
    // Checks if a sales representative with the given username exists in the database
    public boolean existsByUsername(String username);

    // Checks if a sales representative with the given username
    // but not with the given id exists in the database
    boolean existsByUsernameAndIdNot(String username, String id);
}
