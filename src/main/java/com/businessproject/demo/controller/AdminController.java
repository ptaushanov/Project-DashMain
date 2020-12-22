package com.businessproject.demo.controller;

import com.businessproject.demo.model.Admin;
import com.businessproject.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @Autowired
    AdminService adminService;

    // TEMPORARY
    @GetMapping("/admins")
    public String getAdminAddView(Model model) {
        model.addAttribute("admins", adminService.getAdmins());
        return "admins";
    }

    @GetMapping("/new/administrator")
    public String getAdminAddView(@RequestParam("requesterId") String requesterId, Model model) {
        if(adminService.existsAdminById(requesterId))
        {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_admin";
        }
        return "administrator/invalid_requester";
    }

    @PostMapping("/new/administrator")
    public ModelAndView addAdmin(@Valid @ModelAttribute("admin") Admin admin) {

        adminService.saveAdmin(admin);

        ModelAndView mav = new ModelAndView("administrator/admin_added");
        mav.addObject("username", admin.getUsername());
        mav.addObject("requesterId", admin.getAddedById());
        return mav;
    }


    // @DeleteMapping("/admin/representatives")
    // @DeleteMapping("/admin/products")

    // @PutMapping("/admin/admins")
    // @PutMapping("/admin/representatives")
    // @PutMapping("/admin/products")

}
