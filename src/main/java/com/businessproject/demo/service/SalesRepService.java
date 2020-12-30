package com.businessproject.demo.service;

import com.businessproject.demo.repository.CustomerRepository;
import com.businessproject.demo.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesRepService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    public boolean existsSalesRepById(String id) {
        return salesRepRepository.existsById(id);
    }
}
