package com.businessproject.demo.service;

import com.businessproject.demo.exeption.AuthorizationException;
import com.businessproject.demo.exeption.NonExistingEntityException;
import com.businessproject.demo.exeption.UsernameAlreadyExists;
import com.businessproject.demo.model.Admin;
import com.businessproject.demo.model.SalesRepresentative;
import com.businessproject.demo.repository.AdminRepository;
import com.businessproject.demo.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;


    public void saveAdmin(Admin admin) throws UsernameAlreadyExists, AuthorizationException {

        if (!adminRepository.existsById(admin.getAddedById())) {
            throw new AuthorizationException();
        }

        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new UsernameAlreadyExists();
        }

        adminRepository.save(admin);
    }

    public boolean existsAdminById(String id) {
        return adminRepository.existsById(id);
    }

    public boolean existsAdminByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    public Admin getAdminById(String id) throws NonExistingEntityException {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return admin.get();
        } else {
            throw new NonExistingEntityException();
        }
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

    public boolean existsRepresentativeById(String id) {
        return salesRepRepository.existsById(id);
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
        Optional<SalesRepresentative> rep = salesRepRepository.findById(id);
        if (rep.isPresent()) {
            return rep.get();
        } else {
            throw new NonExistingEntityException();
        }
    }

    public void updateRepresentative(SalesRepresentative rep) throws UsernameAlreadyExists, AuthorizationException, NonExistingEntityException {

        if(rep.getId() == null)
        {
            System.err.println("rep id is null");
        }

        if (!adminRepository.existsById(rep.getManagedById())) {
            throw new AuthorizationException();
        }

        if(!salesRepRepository.existsById(rep.getId())){
            throw new NonExistingEntityException();
        }

        if (salesRepRepository.existsByUsername(rep.getUsername())) {
            SalesRepresentative oldRep = salesRepRepository.findById(rep.getId()).get();
            if(!oldRep.getUsername().equals(rep.getUsername())){
                throw new UsernameAlreadyExists();
            }
        }

        salesRepRepository.save(rep);
    }

}
