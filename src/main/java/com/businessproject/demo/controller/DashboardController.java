package com.businessproject.demo.controller;

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
        return "start/select_view";
    }

    @GetMapping("/start/representative")
    public String getSalesRepSelectView(Model model){
        model.addAttribute("entities", dashboardService.getAllSalesReps());
        return "start/select_view";
    }

    @GetMapping("/dashboard")
    public String getDashboardView(@RequestParam("role") String role, @RequestParam("id") String id,Model model){

        Entity entity = dashboardService.getEntityInfo(role, id);
        model.addAttribute("entity", entity);
        model.addAttribute("rolePath", dashboardService.getRole(role));
        return "dashboard/main_view";
    }

}
