package com.businessproject.demo.repository;

import com.businessproject.demo.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    public boolean existsByUsername(String username);

}
