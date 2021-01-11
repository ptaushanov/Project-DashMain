package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    // Checks if the admin with the given username exists in the DB
    public boolean existsByUsername(String username);

}
