package com.businessproject.demo.service;

import com.businessproject.demo.exeption.*;
import com.businessproject.demo.model.dbmodels.Admin;
import com.businessproject.demo.model.dbmodels.Product;
import com.businessproject.demo.model.dbmodels.SaleRecord;
import com.businessproject.demo.model.dbmodels.SalesRepresentative;
import com.businessproject.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromoRepository promoRepository;
    @Autowired
    private SaleRecordRepository saleRecordRepository;

    // Method for saving a new admin to the database
    public void saveAdmin(Admin admin) throws UsernameAlreadyExists, AuthorizationException {
        // Checks if the admin is authorized
        if (!adminRepository.existsById(admin.getAddedById())) {
            throw new AuthorizationException();
        }
        // Checks if the given username for the new admin already exists
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        // Saves the new admin to the database
        adminRepository.save(admin);
    }

    // Method for saving a new sales representative to the database
    public void saveRepresentative(SalesRepresentative rep) throws UsernameAlreadyExists, AuthorizationException {
        // Checks if the admin is authorized
        if (!adminRepository.existsById(rep.getManagedById())) {
            throw new AuthorizationException();
        }
        // Checks if the given username for the new sales representative already exists
        if (salesRepRepository.existsByUsername(rep.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        // Saves the new sales representative to the database
        salesRepRepository.save(rep);
    }

    // Method for saving a new product to the database
    public void saveProduct(Product product) throws AuthorizationException, ProductNameExistsException {
        // Checks if the admin is authorized
        if (!adminRepository.existsById(product.getManagedById())) {
            throw new AuthorizationException();
        }

        // Creating a search string that is the product name
        // but with replaced multiple whitespace characters with a single space
        String searchedString = product
                .getProductName()
                .replaceAll("\\s\\s+", " ");

        // Check if the product is already used in the database
        if (productRepository.existsByProductName(searchedString)) {
            throw new ProductNameExistsException();
        }

        // Save the product to the database
        productRepository.save(product);
    }

    // Check if the admin with the given id exists in the database
    public boolean existsAdminById(String id) {
        return adminRepository.existsById(id);
    }

    // Finds all sales representatives from the database
    public List<SalesRepresentative> getRepresentatives() {
        return salesRepRepository.findAll();
    }

    // Deletes a sales representative with the given id from the database
    public void deleteRepresentativeById(String id) throws NonExistingEntityException {
        if (salesRepRepository.existsById(id)) {
            salesRepRepository.deleteById(id);
        } else {
            throw new NonExistingEntityException();
        }
    }

    // Finds a sales representative with the given id from the database
    // or throws an exception if the representative was not found
    public SalesRepresentative getRepresentativeById(String id) throws NonExistingEntityException {
        return salesRepRepository.findById(id).orElseThrow(NonExistingEntityException::new);
    }

    // Method for updating an existing sales representative
    public void updateRepresentative(SalesRepresentative rep) throws UsernameAlreadyExists, AuthorizationException, NonExistingEntityException {
        // Checks if the sales representative with the given id
        // exists in the database
        if (!salesRepRepository.existsById(rep.getId())) {
            throw new NonExistingEntityException();
        }
        // Checks if the admin is authorized
        if (!adminRepository.existsById(rep.getManagedById())) {
            throw new AuthorizationException();
        }
        // Checks if the username is already in use by another sales representative
        if (salesRepRepository.existsByUsernameAndIdNot(rep.getUsername(), rep.getId())) {
            throw new UsernameAlreadyExists();
        }

        // Updates the sales representative in the database
        salesRepRepository.save(rep);
    }

    // Finds all products in the database
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Finds a product with the given id or throws an exception
    // if the product was not found
    public Product getProductsById(String id) throws NonExistingProductException {
        return productRepository.findById(id).orElseThrow(NonExistingProductException::new);
    }

    // Deletes a product from the database with the given id
    // Deletes all promotion events associated with the product
    public void deleteProductById(String id) throws NonExistingProductException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            // Deletes all promotion events if the product with the given id is present
            if (promoRepository.existsByProductId(id)) {
                promoRepository.deleteByProductId(id);
            }
        } else {
            // Throws an exception if the product is not found in the database
            throw new NonExistingProductException();
        }
    }

    // Method for updating a product
    public void updateProduct(Product product) throws AuthorizationException, NonExistingProductException, ProductNameExistsException {
        // Checks if the product with the given id exists in the database
        if (!productRepository.existsById(product.getId())) {
            throw new NonExistingProductException();
        }
        // Checks if the admin is authorized
        if (!adminRepository.existsById(product.getManagedById())) {
            throw new AuthorizationException();
        }

        // Creating a search string that is the product name
        // but with replaced multiple whitespace characters with a single space
        String searchedString = product
                .getProductName()
                .replaceAll("\\s\\s+", " ");

        // Checks if the product name already in use by another product
        if (productRepository.existsByProductNameAndIdNot(searchedString, product.getId())) {
            throw new ProductNameExistsException();
        }

        // Updates the product in the database
        productRepository.save(product);
    }

    // Finds all sale records for a given sales representative
    public List<SaleRecord> getSalesBySalesRep(String salesRepId) throws NonExistingEntityException {
        // Checks if the sales representative with given id exists in the database
        if (!salesRepRepository.existsById(salesRepId)) {
            throw new NonExistingEntityException();
        }
        return saleRecordRepository.findAllBySalesRepId(salesRepId);
    }

    // Finds all sale records from a start and end date
    public List<SaleRecord> getSalesFromTimeFrame(ZonedDateTime from, ZonedDateTime to) {
        return saleRecordRepository.findSaleRecords(from, to);
    }
}
