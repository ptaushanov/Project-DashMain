package com.businessproject.demo.controller;

import com.businessproject.demo.model.Admin;
import com.businessproject.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/admins")
    public ModelAndView addAdmin(@Valid @ModelAttribute("admin") Admin admin) {
        // TODO Check if username is unique

        if (!admin.getUsername().equals("admin")) {
            adminRepository.save(admin);
        }
        // TODO ELSE Return Error Bad Request
        // TODO And not a view

        ModelAndView mav = new ModelAndView("admin_added");
        mav.addObject("username", admin.getUsername());
        return mav;
    }

    // @PostMapping("/admin/representatives")
    // @PostMapping("/admin/products")

    @GetMapping("/admins")
    public ModelAndView getAdmins() {
        ModelAndView mav = new ModelAndView("admins");
        mav.addObject("admins", adminRepository.findAll());
        return mav;
    }
    // @GetMapping("/admin/representatives")
    // @GetMapping("/admin/products")

    @DeleteMapping("/admins/{id}")
    public void deleteAdmin(@PathVariable String id) {

        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isPresent()) {
            String username = admin.get().getUsername();
            if (!username.equals("admin")) {
                adminRepository.deleteById(id);
            }
            //TODO ELSE return Bad Request
        }
    }

    // @DeleteMapping("/admin/representatives")
    // @DeleteMapping("/admin/products")

    // @PutMapping("/admin/admins")
    // @PutMapping("/admin/representatives")
    // @PutMapping("/admin/products")

}
