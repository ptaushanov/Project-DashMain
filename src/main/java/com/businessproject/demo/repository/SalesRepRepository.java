package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.SalesRepresentative;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesRepRepository extends MongoRepository<SalesRepresentative, String> {
    public boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, String id);
}
