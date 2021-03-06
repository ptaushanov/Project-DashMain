package com.businessproject.demo.controller;

import com.businessproject.demo.exeption.*;
import com.businessproject.demo.model.dbmodels.Admin;
import com.businessproject.demo.model.dbmodels.Product;
import com.businessproject.demo.model.dbmodels.SalesRepresentative;
import com.businessproject.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.HashMap;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    // Most of the request if not all are authorized through 
    // something called "requesterId", that is the sales representative
    // or admin that make the specific request must authorize with their id

    @Autowired
    AdminService adminService;

    // GET mapping for getting the "Add Admin page"
    @GetMapping("/new/administrator")
    public String getAdminAddView(@RequestParam("requesterId") String requesterId, Model model) {
        // Checking if the admin with their id exists in the database
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_admin";
        }

        // If not return the view from the "invalid request" template
        return "administrator/invalid_request";
    }

    // GET mapping for getting the "Add Representative page"
    @GetMapping("/new/representative")
    public String getRepresentativeAddView(@RequestParam("requesterId") String requesterId, Model model) {
        // Checking if the admin with their id exists in the database
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            return "administrator/add_rep";
        }
        // If not return the view from the "invalid request" template
        return "administrator/invalid_request";
    }

    // GET mapping for getting the "Manage Representatives page"
    @GetMapping("/manage/representatives")
    public String getRepresentativeManageView(@RequestParam("requesterId") String requesterId, Model model) {
        // Checking if the admin with their id exists in the database
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("representatives", adminService.getRepresentatives());

            return "administrator/manage_reps";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        // If not return the view from the "invalid request" template
        return "administrator/invalid_request";
    }

    // GET mapping for getting the "Update Representative page"
    @GetMapping("/update/representative")
    public String getEditRepresentativeView(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
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
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("products", adminService.getProducts());

            return "administrator/manage_products";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "administrator/invalid_request";
    }

    @GetMapping("/analyze/sales/representative")
    public String getAnalyzeSalesByRepView(@RequestParam("requesterId") String requesterId, @RequestParam(value = "targetId", required = false) String targetId, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("requesterId", requesterId);
                put("pickBy", "salesRep");
                put("salesReps", adminService.getRepresentatives());
            }});
            if (targetId != null) {
                try {
                    model.addAttribute("sales", adminService.getSalesBySalesRep(targetId));
                } catch (NonExistingEntityException exception) {
                    model.addAttribute("errorMessage", exception.getMessage());
                    return "administrator/invalid_request";
                }
            }
            return "administrator/sales_analysis";
        }
        return "administrator/invalid_request";
    }

    @GetMapping("/analyze/sales/timeframe")
    public String getAnalyzeSalesByTimeframeView(@RequestParam("requesterId") String requesterId, @RequestParam(value = "from", required = false) ZonedDateTime from, @RequestParam(value = "to", required = false) ZonedDateTime to, Model model) {
        if (adminService.existsAdminById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("pickBy", "timeframe");
            if (from != null && to != null) {
                model.addAttribute("sales", adminService.getSalesFromTimeFrame(from, to));
            }
            return "administrator/sales_analysis";
        }
        return "administrator/invalid_request";
    }

    @PostMapping("/new/administrator")
    public String addAdmin(@Valid @ModelAttribute("admin") Admin admin, Model model) {
        try {
            adminService.saveAdmin(admin);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("username", admin.getUsername());
                put("entityName", "admin");
                put("requesterId", admin.getAddedById());
            }});
        } catch (UsernameAlreadyExists exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", admin.getAddedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "administrator/invalid_request";
        }
        return "administrator/entity_added";
    }

    @PostMapping("/new/representative")
    public String addRepresentative(@Valid @ModelAttribute("representative") SalesRepresentative rep, Model model) {
        try {
            adminService.saveRepresentative(rep);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("username", rep.getUsername());
                put("entityName", "representative");
                put("requesterId", rep.getManagedById());
            }});
        } catch (UsernameAlreadyExists exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", rep.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "administrator/invalid_request";
        }
        return "administrator/entity_added";
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
    public String addProduct(@Valid @ModelAttribute("product") Product product, Model model) {
        try {
            adminService.saveProduct(product);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("productName", product.getProductName());
                put("requesterId", product.getManagedById());
            }});
        } catch (ProductNameExistsException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", product.getManagedById());
            }});
        } catch (AuthorizationException authorizationException) {
            model.addAttribute("errorMessage", authorizationException.getMessage());
            return "administrator/invalid_request";
        }
        return "administrator/product_added";
    }

    @PostMapping("/update/product")
    public String updateProduct(@Valid @ModelAttribute("product") Product product, Model model) {
        try {
            adminService.updateProduct(product);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", product.getManagedById());
            }});
        } catch (NonExistingProductException | ProductNameExistsException exception) {
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
