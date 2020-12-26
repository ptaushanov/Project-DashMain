package com.businessproject.demo.controller;

import com.businessproject.demo.exeption.InvalidRoleException;
import com.businessproject.demo.exeption.NonExistingEntityException;
import com.businessproject.demo.model.Entity;
import com.businessproject.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/")
    public String getMainView() {
        return "index";
    }

    @GetMapping("/start")
    public String getStartView() {
        return "start/start_view";
    }

    @GetMapping("/start/administrator")
    public String getAdminSelectView(Model model){
        model.addAttribute("entities", dashboardService.getAllAdmins());
        model.addAttribute("role", "admin");
        return "start/select_view";
    }

    @GetMapping("/start/representative")
    public String getSalesRepSelectView(Model model){
        model.addAttribute("entities", dashboardService.getAllSalesReps());
        model.addAttribute("role", "salesRep");
        return "start/select_view";
    }

    @GetMapping("/dashboard")
    public String getDashboardView(@RequestParam(value = "role", required = false) String role, @RequestParam(value = "id", required = false) String id,Model model){

        if(role == null || id == null){
            model.addAttribute("errorMessage", "Not a valid request was provided for accessing the dashboard page!");
            return "dashboard/error_view";
        }

        Entity entity;
        try {
            entity = dashboardService.getEntityInfo(role, id);
            model.addAttribute("entity", entity);
            model.addAttribute("rolePath", dashboardService.getRole(role));
            return "dashboard/main_view";

        } catch (InvalidRoleException | NonExistingEntityException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "dashboard/error_view";
        }

    }

}
