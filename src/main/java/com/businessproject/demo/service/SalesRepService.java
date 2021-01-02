package com.businessproject.demo.service;

import com.businessproject.demo.exeption.AuthorizationException;
import com.businessproject.demo.exeption.NonExistingCustomerException;
import com.businessproject.demo.exeption.PhoneNumberAlreadyExists;
import com.businessproject.demo.model.Customer;
import com.businessproject.demo.model.Product;
import com.businessproject.demo.model.PromoEvent;
import com.businessproject.demo.repository.CustomerRepository;
import com.businessproject.demo.repository.ProductRepository;
import com.businessproject.demo.repository.PromoRepository;
import com.businessproject.demo.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesRepService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromoRepository promoRepository;

    public boolean existsSalesRepById(String id) {
        return salesRepRepository.existsById(id);
    }

    public void saveCustomer(Customer customer) throws PhoneNumberAlreadyExists, AuthorizationException {
        if (!salesRepRepository.existsById(customer.getManagedById())) {
            throw new AuthorizationException();
        }
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExists();
        }
        customerRepository.save(customer);
    }

    public List<Customer> getCustomers(String salesRepId) {
        return customerRepository.findAllByManagedById(salesRepId);
    }

    public Customer getCustomerById(String id) throws NonExistingCustomerException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new NonExistingCustomerException();
        }
    }

    public void deleteRepresentativeById(String id) throws NonExistingCustomerException {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new NonExistingCustomerException();
        }
    }

    public void updateCustomer(Customer customer) throws NonExistingCustomerException, AuthorizationException, PhoneNumberAlreadyExists {
        if (!customerRepository.existsById(customer.getId())) {
            throw new NonExistingCustomerException();
        }
        if (!salesRepRepository.existsById(customer.getManagedById())) {
            throw new AuthorizationException();
        }
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            Customer oldCustomer = customerRepository.findById(customer.getId()).get();
            if (!oldCustomer.getPhoneNumber().equals(customer.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExists();
            }
        }
        customerRepository.save(customer);
    }

    public List<PromoEvent> getPromoEvents() {
        return promoRepository.findAll();
    }

    public List<Product> getNonPromoProducts() {
        // The RAM eater

        List<Product> promoProducts = promoRepository
                .findAll()
                .stream()
                .map(PromoEvent::getProduct)
                .collect(Collectors.toList());

        return productRepository
                .findAll()
                .stream()
                .filter(product -> !promoProducts.contains(product))
                .collect(Collectors.toList());
    }

    public void savePromoEvent(PromoEvent promoEvent) {
        
    }
}
