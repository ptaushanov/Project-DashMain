package com.businessproject.demo.service;
import com.businessproject.demo.exeption.AuthorizationException;
import com.businessproject.demo.exeption.UsernameAlreadyExists;
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


    public void saveAdmin(Admin admin) throws UsernameAlreadyExists, AuthorizationException{

        if(!adminRepository.existsById(admin.getAddedById())){
            throw new AuthorizationException();
        }

        if(adminRepository.existsByUsername(admin.getUsername())){
            throw new UsernameAlreadyExists();
        }

        adminRepository.save(admin);
    }

    public boolean existsAdminById(String id){
        return  adminRepository.existsById(id);
    }

    public  boolean existsAdminByUsername(String username){
        return adminRepository.existsByUsername(username);
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
