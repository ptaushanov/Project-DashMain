package com.businessproject.demo.repository;

import com.businessproject.demo.model.SalesRepresentative;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesRepRepository extends MongoRepository<SalesRepresentative, String> {




}