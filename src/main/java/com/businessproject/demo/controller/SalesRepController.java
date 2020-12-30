package com.businessproject.demo.controller;

import com.businessproject.demo.service.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class SalesRepController {
    @Autowired
    private SalesRepService salesRepService;

    @GetMapping("/new/customer")
    // TODO make request param optional
    public String getCustomerAddView(@RequestParam("requesterId") String requesterId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "representative/add_customer";
        }
        return "representative/invalid_request";
    }
}
