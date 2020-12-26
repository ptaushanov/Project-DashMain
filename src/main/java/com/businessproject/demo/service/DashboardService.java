package com.businessproject.demo.service;

import com.businessproject.demo.exeption.InvalidRoleException;
import com.businessproject.demo.exeption.NonExistingEntityException;
import com.businessproject.demo.model.Admin;
import com.businessproject.demo.model.Entity;
import com.businessproject.demo.model.SalesRepresentative;
import com.businessproject.demo.repository.AdminRepository;
import com.businessproject.demo.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public  List<SalesRepresentative> getAllSalesReps(){
        return  salesRepRepository.findAll();
    }
    public Entity getEntityInfo(String role, String id) throws InvalidRoleException, NonExistingEntityException {
        if(role.equals("admin")) {
            Optional<Admin> admin = adminRepository.findById(id);
            if (admin.isPresent()){
                return admin.get();
            }
            else {
                throw new NonExistingEntityException();
            }
        }
        else if(role.equals("salesRep")) {
            Optional<SalesRepresentative> salesRep = salesRepRepository.findById(id);
            if (salesRep.isPresent()){
                return salesRep.get();
            }
            else{
                throw new NonExistingEntityException();
            }
        }
        else {
            throw new InvalidRoleException();
        }
    }

    public String getRole(String role) throws NonExistingEntityException {
        if(role.equals("admin")){
            return "administrator";
        }
        if(role.equals("salesRep")){
            return  "representative";
        }
        else {
            throw new NonExistingEntityException();
        }
    }
}
