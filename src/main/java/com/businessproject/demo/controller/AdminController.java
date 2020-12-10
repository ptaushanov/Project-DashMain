package com.businessproject.demo.controller;

import com.businessproject.demo.model.Admin;
import com.businessproject.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/admins")
    public String addAdmin(@Valid @RequestBody Admin admin) {
        adminRepository.save(admin);
        return "Added new admin!";
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

    // @DeleteMapping("/admin/admins")
    // @DeleteMapping("/admin/representatives")
    // @DeleteMapping("/admin/products")

    // @PutMapping("/admin/admins")
    // @PutMapping("/admin/representatives")
    // @PutMapping("/admin/products")

}
