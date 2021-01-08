package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    public boolean existsByPhoneNumber(String phoneNumber);

    public List<Customer> findAllByManagedById(String salesRepId);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
