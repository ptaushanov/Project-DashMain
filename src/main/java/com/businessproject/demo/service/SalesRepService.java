package com.businessproject.demo.service;

import com.businessproject.demo.exeption.*;
import com.businessproject.demo.model.dbmodels.*;
import com.businessproject.demo.model.info.CustomerInfo;
import com.businessproject.demo.model.info.ProductInfo;
import com.businessproject.demo.model.info.PromoInfo;
import com.businessproject.demo.model.info.SalesRepInfo;
import com.businessproject.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    @Autowired
    private SaleRecordRepository saleRecordRepository;

    // Checks if a sales representative with the given id exists in the database
    public boolean existsSalesRepById(String id) {
        return salesRepRepository.existsById(id);
    }

    // Method for saving customers to the database
    public void saveCustomer(Customer customer) throws PhoneNumberAlreadyExists, AuthorizationException {
        // Checks if the sales representative is authorized
        if (!salesRepRepository.existsById(customer.getManagedById())) {
            throw new AuthorizationException();
        }
        // Checks if the phone number is already used by another customer
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExists();
        }
        // Saves the customer to the database
        customerRepository.save(customer);
    }

    // Finds all customers associated with the given sales representative from the database
    public List<Customer> getCustomers(String salesRepId) {
        return customerRepository.findAllByManagedById(salesRepId);
    }

    // Finds a customer with the given id in the database
    public Customer getCustomerById(String id) throws NonExistingCustomerException {
        return customerRepository.findById(id).orElseThrow(NonExistingCustomerException::new);
    }

    // Deletes a customer with the given id from the database
    public void deleteRepresentativeById(String id) throws NonExistingCustomerException {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new NonExistingCustomerException();
        }
    }

    // Method for updating the customer in the database
    public void updateCustomer(Customer customer) throws NonExistingCustomerException, AuthorizationException, PhoneNumberAlreadyExists {
        // Checks if customer with the given id exists in the database
        if (!customerRepository.existsById(customer.getId())) {
            throw new NonExistingCustomerException();
        }
        // Checks sales representative is authorized
        if (!salesRepRepository.existsById(customer.getManagedById())) {
            throw new AuthorizationException();
        }
        // Checks if the phone number is already in use by another customer
        if (customerRepository.existsByPhoneNumberAndIdNot(customer.getPhoneNumber(), customer.getId())) {
            throw new PhoneNumberAlreadyExists();
        }
        // Updates the customer in the database
        customerRepository.save(customer);
    }

    // Method for receiving the status for a promotion event
    private String getStatus(ZonedDateTime startDate, ZonedDateTime endDate) {
        // Gets the current date and time
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());

        // Checks if the current date and time is before, after or in between
        // the start and end dates
        if (currentDate.isBefore(startDate)) {
            return "Upcoming";
        } else if (currentDate.isAfter(endDate)) {
            return "Expired";
        } else {
            return "Active";
        }
    }

    // Method for creating promotion information objects from
    // a promotion event and a given product
    public List<PromoInfo> getPromoInfo(String salesRepId) {
        return promoRepository // In the promotion repository
                .findAllByManagedById(salesRepId) // Find all promotion for a given sales representative
                .stream() // Create a stream of PromoEvent objects
                .map(x -> new PromoInfo(x, productRepository
                        .findById(x.getProductId())
                        .orElse(null), getStatus(x.getStartDate(), x.getEndDate())
                )) // Map them to a new PromoInfo object with the current PromoEvent and Product
                .filter(y -> y.getPromoProduct() != null) // Filter out all null Products
                .collect(Collectors.toList()); // Create a list of PromoInfo objects
    }

    // Method for finding all non promotional products
    public List<Product> getNonPromoProducts(String salesRepId) {
        return productRepository // In the product repository
                .findAll() // Find all products
                .stream() // Create a stream of Product objects and filter out the promotional ones
                .filter(product -> !promoRepository.existsByProductIdAndManagedById(product.getId(), salesRepId))
                .collect(Collectors.toList()); // Create a list of Product objects
    }

    // Method for saving promotional events to the database
    public void savePromoEvent(PromoEvent promoEvent) throws AuthorizationException, NonExistingProductException, PromotionAlreadyActive, MismatchedDateException {
        // Checks if the sales representative is authorized
        if (!salesRepRepository.existsById(promoEvent.getManagedById())) {
            throw new AuthorizationException();
        }
        // Checks if the product exists in the database
        if (!productRepository.existsById(promoEvent.getProductId())) {
            throw new NonExistingProductException();
        }
        // Checks if a promotion event for the same product is present in the database
        if (promoRepository.existsByProductIdAndManagedById(promoEvent.getProductId(), promoEvent.getManagedById())) {
            throw new PromotionAlreadyActive();
        }

        // Current date and time
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        // Checks if the start and end date are valid
        if (promoEvent.getEndDate().isBefore(promoEvent.getStartDate())) {
            throw new MismatchedDateException("End date can't be before start date!");
        } else if (promoEvent.getEndDate().isEqual(promoEvent.getStartDate())) {
            throw new MismatchedDateException("End and start dates can't be the same!");
        } else if (promoEvent.getStartDate().isBefore(now.toLocalDate().atStartOfDay(ZoneId.systemDefault()))) {
            throw new MismatchedDateException("Start date can't be before current date!");
        }

        // Saving the promotion event to the database
        promoRepository.save(promoEvent);
    }

    // Finds a product with the given id in the database
    public Product getProductById(String targetId) throws NonExistingProductException {
        return productRepository.findById(targetId).orElseThrow(NonExistingProductException::new);
    }

    // Finds all products in the database
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Method for saving sale records to the database
    public void saveSaleRecord(SaleRecord saleRecord) throws AuthorizationException, NonExistingCustomerException, NonExistingProductException, InsufficientQuantityException {
        final String saleRepId = saleRecord.getSalesRepId();
        final String productId = saleRecord.getProductId();
        final String customerId = saleRecord.getCustomerId();

        // Finds the sales representative with the given id in the database or throws an exception
        SalesRepresentative salesRep = salesRepRepository
                .findById(saleRepId)
                .orElseThrow(AuthorizationException::new);

        // Finds the customer with the given id and sales representative id
        // in the database or throws an exception
        Customer customer = customerRepository
                .findByIdAndManagedById(customerId, saleRepId)
                .orElseThrow(NonExistingCustomerException::new);

        // Finds the product with the given id or throws an exception
        Product product = productRepository
                .findById(productId)
                .orElseThrow(NonExistingProductException::new);

        // Sets all info objects from the provided sales representative, customer and product
        saleRecord.setSalesRepInfo(SalesRepInfo.ofSalesRepresentative(salesRep));
        saleRecord.setCustomerInfo(CustomerInfo.ofCustomer(customer));
        saleRecord.setProductInfo(ProductInfo.ofProduct(product));

        // Current date and time without any zone offset, meaning +00:00
        ZonedDateTime currentDateTime = ZonedDateTime.ofInstant(LocalDateTime.now(), ZoneOffset.UTC, ZoneId.systemDefault());
        // Setting the "madeAt" property of the sale record to the current date and time
        saleRecord.setMadeAt(currentDateTime);

        // Trying to find a promotion event with the given sales representative id
        // productId and current date and time
        Optional<PromoEvent> promoEvent = promoRepository.findPromoEvent(saleRepId, productId, currentDateTime);

        // If there is an active promotion event
        if (promoEvent.isPresent()) {
            // Set the record's price to the promotional one
            saleRecord.setPrice(promoEvent.get().getPromoPrice());
        } else {
            // Else set the record's price to the default product price
            saleRecord.setPrice(product.getPrice());
        }

        // Modifying the quantity of product
        int productQuantity = product.getQuantity();
        // If the quantity of the product is enough to satisfy the requested quantity
        if (productQuantity - saleRecord.getQuantity() >= 0) {
            // Decrease the quantity of the product by the requested quantity
            product.setQuantity(productQuantity - saleRecord.getQuantity());
            // Update the quantity of the product in the database
            productRepository.save(product);
        } else throw new InsufficientQuantityException();

        // Saving the sale record in the database
        saleRecordRepository.save(saleRecord);
    }

    // Finds all sale records with the given sales representative
    public List<SaleRecord> getSales(String requesterId) {
        return saleRecordRepository.findAllBySalesRepId(requesterId);
    }
}
