package com.businessproject.demo.service;
import com.businessproject.demo.model.Admin;
import com.businessproject.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;


    public void saveAdmin(Admin admin) {
        // TODO Check if username is unique
        // TODO Check if the admin who adds the new admin is valid

        if (!admin.getUsername().equals("admin")) {
            adminRepository.save(admin);
        }

        // TODO ELSE Return Error Bad Request
        // TODO And not a view
    }

    public boolean existsAdminById(String id){
        return  adminRepository.existsById(id);
    }

    public Admin getAdminById(String id) {
        if(adminRepository.existsById(id)){
            Optional<Admin> admin = adminRepository.findById(id);
            if (admin.isPresent())
            {
                return admin.get();
            }
        }
        else {
            // TODO Return Error Bad Request
        }

        //TODO Remove later
        return null;
    }

    //TEMPORARY
    public List<Admin> getAdmins() {
        return adminRepository.findAll();
    }
}
