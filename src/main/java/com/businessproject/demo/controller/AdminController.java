package com.businessproject.demo.controller;

import com.businessproject.demo.exeption.AuthorizationException;
import com.businessproject.demo.exeption.NonExistingEntityException;
import com.businessproject.demo.exeption.NonExistingProductException;
import com.businessproject.demo.exeption.UsernameAlreadyExists;
import com.businessproject.demo.model.Admin;
import com.businessproject.demo.model.Product;
import com.businessproject.demo.model.SalesRepresentative;
import com.businessproject.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/new/administrator")
    // TODO make request param optional

    public String getAdminAddView(@RequestParam("requesterId") String requesterId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_admin";
        }
        return "administrator/invalid_request";
    }

    @GetMapping("/new/representative")
    // TODO make request param optional

    public String getRepresentativeAddView(@RequestParam("requesterId") String requesterId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_rep";
        }
        return "administrator/invalid_request";
    }

    @GetMapping("/manage/representatives")
    public String getRepresentativeManageView(@RequestParam("requesterId") String requesterId, Model model) {
        // TODO make request param optional

        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("representatives", adminService.getRepresentatives());

            return "administrator/manage_reps";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/update/representative")
    public String getEditRepresentativeView(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        // TODO make request param optional

        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                model.addAttribute("representative", adminService.getRepresentativeById(targetId));
            } catch (NonExistingEntityException exception) {
                model.addAttribute("errorMessage", "Requested a non existing representative.");
                return "administrator/invalid_request";
            }

            return "administrator/update_rep";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/delete/representative")
    public String deleteRepresentative(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                String targetUsername = adminService.getRepresentativeById(targetId).getUsername();
                adminService.deleteRepresentativeById(targetId);

                model.addAttribute("username", targetUsername);
                model.addAttribute("isError", false);
            } catch (NonExistingEntityException exception) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMessage", exception.getMessage());
            }
            return "administrator/rep_deleted";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized source are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/new/product")
    public String getProductAddView(@RequestParam("requesterId") String requesterId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_product";
        }
        return "administrator/invalid_request";
    }

    @GetMapping("/update/product")
    public String getEditProductView(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        // TODO make request param optional

        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                model.addAttribute("product", adminService.getProductsById(targetId));
            } catch (NonExistingProductException exception) {
                model.addAttribute("errorMessage", "Requested a non existing product.");
                return "administrator/invalid_request";
            }
            return "administrator/update_product";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/delete/product")
    public String deleteProduct(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                String targetUsername = adminService.getProductsById(targetId).getProductName();
                adminService.deleteProductById(targetId);

                model.addAttribute("productName", targetUsername);
                model.addAttribute("isError", false);
            } catch (NonExistingProductException exception) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMessage", exception.getMessage());
            }
            return "administrator/product_deleted";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized source are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/manage/products")
    public String getProductManageView(@RequestParam("requesterId") String requesterId, Model model) {
        // TODO make request param optional

        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("products", adminService.getProducts());

            return "administrator/manage_products";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "administrator/invalid_request";
    }

    @PostMapping("/new/administrator")
    // TODO make request param optional

    public ModelAndView addAdmin(@Valid @ModelAttribute("admin") Admin admin) {

        ModelAndView mav = new ModelAndView("administrator/entity_added");
        try {
            adminService.saveAdmin(admin);

            mav.addObject("username", admin.getUsername());
            mav.addObject("entityName", "admin");
            mav.addObject("requesterId", admin.getAddedById());
            mav.addObject("isError", false);
        } catch (UsernameAlreadyExists exception) {
            mav.addObject("isError", true);
            mav.addObject("errorMessage", exception.getMessage());
            mav.addObject("requesterId", admin.getAddedById());
        } catch (AuthorizationException exception) {
            mav = new ModelAndView("administrator/invalid_request");
            mav.addObject("errorMessage", exception.getMessage());
        }

        return mav;
    }

    @PostMapping("/new/representative")
    public ModelAndView addRepresentative(@Valid @ModelAttribute("representative") SalesRepresentative rep) {

        ModelAndView mav = new ModelAndView("administrator/entity_added");
        try {
            adminService.saveRepresentative(rep);
            mav.addObject("isError", false);

            mav.addObject("username", rep.getUsername());
            mav.addObject("entityName", "representative");
            mav.addObject("requesterId", rep.getManagedById());
        } catch (UsernameAlreadyExists usernameAlreadyExists) {
            mav.addObject("isError", true);
            mav.addObject("errorMessage", usernameAlreadyExists.getMessage());
            mav.addObject("requesterId", rep.getManagedById());

        } catch (AuthorizationException authorizationException) {
            mav = new ModelAndView("administrator/invalid_request");
            mav.addObject("errorMessage", authorizationException.getMessage());
        }

        return mav;
    }

    @PostMapping("/update/representative")
    public String updateRepresentative(@Valid @ModelAttribute("representative") SalesRepresentative rep, Model model) {
        try {
            adminService.updateRepresentative(rep);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", rep.getManagedById());
            }});
        } catch (UsernameAlreadyExists | NonExistingEntityException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", rep.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "administrator/invalid_request";
        }
        return "administrator/rep_updated";
    }

    @PostMapping("/new/product")
    public ModelAndView addProduct(@Valid @ModelAttribute("product") Product product) {

        ModelAndView mav = new ModelAndView("administrator/product_added");
        try {
            adminService.saveProduct(product);
            mav.addObject("productName", product.getProductName());
            mav.addObject("requesterId", product.getManagedById());
        } catch (AuthorizationException authorizationException) {
            mav = new ModelAndView("administrator/invalid_request");
            mav.addObject("errorMessage", authorizationException.getMessage());
        }
        return mav;
    }

    @PostMapping("/update/product")
    public String updateProduct(@Valid @ModelAttribute("product") Product product, Model model) {
        try {
            adminService.updateProduct(product);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", product.getManagedById());
            }});
        } catch (NonExistingProductException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", product.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "administrator/invalid_request";
        }
        return "administrator/product_updated";
    }
}
