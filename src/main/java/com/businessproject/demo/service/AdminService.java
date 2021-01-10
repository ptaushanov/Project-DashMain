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


    public void saveAdmin(Admin admin) throws UsernameAlreadyExists, AuthorizationException {
        if (!adminRepository.existsById(admin.getAddedById())) {
            throw new AuthorizationException();
        }
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        adminRepository.save(admin);
    }

    public void saveRepresentative(SalesRepresentative rep) throws UsernameAlreadyExists, AuthorizationException {
        if (!adminRepository.existsById(rep.getManagedById())) {
            throw new AuthorizationException();
        }
        if (salesRepRepository.existsByUsername(rep.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        salesRepRepository.save(rep);
    }

    public void saveProduct(Product product) throws AuthorizationException, ProductNameExistsException {
        if (!adminRepository.existsById(product.getManagedById())) {
            throw new AuthorizationException();
        }
        String searchedString = product
                .getProductName()
                .replaceAll("\\s\\s+", " ")
                .trim();
        if (productRepository.existsByProductName(searchedString)) {
            throw new ProductNameExistsException();
        }
        productRepository.save(product);
    }

    public boolean existsAdminById(String id) {
        return adminRepository.existsById(id);
    }

    public List<SalesRepresentative> getRepresentatives() {
        return salesRepRepository.findAll();
    }

    public void deleteRepresentativeById(String id) throws NonExistingEntityException {
        if (salesRepRepository.existsById(id)) {
            salesRepRepository.deleteById(id);
        } else {
            throw new NonExistingEntityException();
        }
    }

    public SalesRepresentative getRepresentativeById(String id) throws NonExistingEntityException {
        return salesRepRepository.findById(id).orElseThrow(NonExistingEntityException::new);
    }

    public void updateRepresentative(SalesRepresentative rep) throws UsernameAlreadyExists, AuthorizationException, NonExistingEntityException {
        if (!salesRepRepository.existsById(rep.getId())) {
            throw new NonExistingEntityException();
        }
        if (!adminRepository.existsById(rep.getManagedById())) {
            throw new AuthorizationException();
        }
        if (salesRepRepository.existsByUsername(rep.getUsername())) {
            SalesRepresentative oldRep = salesRepRepository.findById(rep.getId()).get();
            if (!oldRep.getUsername().equals(rep.getUsername())) {
                throw new UsernameAlreadyExists();
            }
        }
        salesRepRepository.save(rep);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductsById(String id) throws NonExistingProductException {
        return productRepository.findById(id).orElseThrow(NonExistingProductException::new);

    }

    public void deleteProductById(String id) throws NonExistingProductException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            if (promoRepository.existsByProductId(id)) {
                promoRepository.deleteByProductId(id);
            }
        } else {
            throw new NonExistingProductException();
        }
    }

    public void updateProduct(Product product) throws AuthorizationException, NonExistingProductException, ProductNameExistsException {
        if (!productRepository.existsById(product.getId())) {
            throw new NonExistingProductException();
        }
        if (!adminRepository.existsById(product.getManagedById())) {
            throw new AuthorizationException();
        }

        String searchedString = product
                .getProductName()
                .replaceAll("\\s\\s+", " ")
                .trim();

        if (productRepository.existsByProductNameAndIdNot(searchedString, product.getId())) {
            throw new ProductNameExistsException();
        }
        productRepository.save(product);
    }

    public List<SaleRecord> getSalesBySalesRep(String salesRepId) throws NonExistingEntityException {
        if (!salesRepRepository.existsById(salesRepId)) {
            throw new NonExistingEntityException();
        }
        return saleRecordRepository.findAllBySalesRepId(salesRepId);
    }

    public List<SaleRecord> getSalesFromTimeFrame(ZonedDateTime from, ZonedDateTime to) {
        return saleRecordRepository.findSaleRecords(from, to);
    }
}
