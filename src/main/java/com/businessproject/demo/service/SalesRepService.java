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
        return customerRepository.findById(id).orElseThrow(NonExistingCustomerException::new);
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

    private String getStatus(ZonedDateTime startDate, ZonedDateTime endDate) {
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());

        if (currentDate.isBefore(startDate)) {
            return "Upcoming";
        } else if (currentDate.isAfter(endDate)) {
            return "Expired";
        } else {
            return "Active";
        }
    }

    public List<PromoInfo> getPromoInfo(String salesRepId) {
        return promoRepository
                .findAllByManagedById(salesRepId)
                .stream()
                .map(x -> new PromoInfo(x, productRepository
                        .findById(x.getProductId())
                        .orElse(null), getStatus(x.getStartDate(), x.getEndDate())
                ))
                .filter(y -> y.getPromoProduct() != null)
                .collect(Collectors.toList());

    }

    public List<Product> getNonPromoProducts(String salesRepId) {
        return productRepository
                .findAll()
                .stream()
                .filter(product -> !promoRepository.existsByProductIdAndManagedById(product.getId(), salesRepId))
                .collect(Collectors.toList());
    }

    public void savePromoEvent(PromoEvent promoEvent) throws AuthorizationException, NonExistingProductException, PromotionAlreadyActive, MismatchedDateException {
        if (!salesRepRepository.existsById(promoEvent.getManagedById())) {
            throw new AuthorizationException();
        }
        if (!productRepository.existsById(promoEvent.getProductId())) {
            throw new NonExistingProductException();
        }
        if (promoRepository.existsByProductIdAndManagedById(promoEvent.getProductId(), promoEvent.getManagedById())) {
            throw new PromotionAlreadyActive();
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        if (promoEvent.getEndDate().isBefore(promoEvent.getStartDate())) {
            throw new MismatchedDateException("End date can't be before start date!");
        } else if (promoEvent.getEndDate().isEqual(promoEvent.getStartDate())) {
            throw new MismatchedDateException("End and start dates can't be the same!");
        } else if (promoEvent.getStartDate().isBefore(now.toLocalDate().atStartOfDay(ZoneId.systemDefault()))) {
            throw new MismatchedDateException("Start date can't be before current date!");
        }
        promoRepository.save(promoEvent);
    }

    public Product getProductById(String targetId) throws NonExistingProductException {
        return productRepository.findById(targetId).orElseThrow(NonExistingProductException::new);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void saveSaleRecord(SaleRecord saleRecord) throws AuthorizationException, NonExistingCustomerException, NonExistingProductException, InsufficientQuantityException {
        final String saleRepId = saleRecord.getSalesRepId();
        final String productId = saleRecord.getProductId();
        final String customerId = saleRecord.getCustomerId();

        SalesRepresentative salesRep = salesRepRepository
                .findById(saleRepId)
                .orElseThrow(AuthorizationException::new);

        Customer customer = customerRepository
                .findByIdAndManagedById(customerId, saleRepId)
                .orElseThrow(NonExistingCustomerException::new);

        Product product = productRepository
                .findById(productId)
                .orElseThrow(NonExistingProductException::new);

        saleRecord.setSalesRepInfo(SalesRepInfo.ofSalesRepresentative(salesRep));
        saleRecord.setCustomerInfo(CustomerInfo.ofCustomer(customer));
        saleRecord.setProductInfo(ProductInfo.ofProduct(product));

        ZonedDateTime currentDateTime = ZonedDateTime.ofInstant(LocalDateTime.now(), ZoneOffset.UTC, ZoneId.systemDefault());
        saleRecord.setMadeAt(currentDateTime);

        Optional<PromoEvent> promoEvent = promoRepository.findPromoEvent(saleRepId, productId, currentDateTime);

        if (promoEvent.isPresent()) {
            saleRecord.setPrice(promoEvent.get().getPromoPrice());
        } else {
            saleRecord.setPrice(product.getPrice());
        }

        int productQuantity = product.getQuantity();
        if (productQuantity - saleRecord.getQuantity() >= 0) {
            // Unsafe
            product.setQuantity(productQuantity - saleRecord.getQuantity());
            productRepository.save(product);
        } else throw new InsufficientQuantityException();

        saleRecordRepository.save(saleRecord);
    }

    public List<SaleRecord> getSales(String requesterId) {
        return saleRecordRepository.findAllBySalesRepId(requesterId);
    }
}
