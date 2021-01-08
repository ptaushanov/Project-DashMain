package com.businessproject.demo.service;

import com.businessproject.demo.exeption.InvalidRoleException;
import com.businessproject.demo.exeption.NonExistingEntityException;
import com.businessproject.demo.model.dbmodels.Admin;
import com.businessproject.demo.model.dbmodels.Entity;
import com.businessproject.demo.model.dbmodels.SalesRepresentative;
import com.businessproject.demo.repository.AdminRepository;
import com.businessproject.demo.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public List<SalesRepresentative> getAllSalesReps() {
        return salesRepRepository.findAll();
    }

    public Entity getEntityInfo(String role, String id) throws InvalidRoleException, NonExistingEntityException {
        if (role.equals("admin")) {
            return adminRepository.findById(id).orElseThrow(NonExistingEntityException::new);
        } else if (role.equals("salesRep")) {
            return salesRepRepository.findById(id).orElseThrow(NonExistingEntityException::new);
        } else {
            throw new InvalidRoleException();
        }
    }

    public String getRole(String role) throws NonExistingEntityException {
        if (role.equals("admin")) {
            return "administrator";
        }
        if (role.equals("salesRep")) {
            return "representative";
        } else {
            throw new NonExistingEntityException();
        }
    }
}
