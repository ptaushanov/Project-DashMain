package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Checks if a customer with the given phone number exists in the database
    public boolean existsByPhoneNumber(String phoneNumber);

    // Finds all customers that are managed by the same sales representative
    public List<Customer> findAllByManagedById(String salesRepId);

    // Finds a customer with a given id that are managed by the same sales representative
    Optional<Customer> findByIdAndManagedById(String customerId, String salesRepId);

    // Checks if a customer with the given phone number but not with the given id
    // exists in the database
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, String id);
}
