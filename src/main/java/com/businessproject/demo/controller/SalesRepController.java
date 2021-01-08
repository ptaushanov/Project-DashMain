package com.businessproject.demo.controller;

import com.businessproject.demo.exeption.*;
import com.businessproject.demo.model.dbmodels.Customer;
import com.businessproject.demo.model.dbmodels.Product;
import com.businessproject.demo.model.dbmodels.PromoEvent;
import com.businessproject.demo.model.dbmodels.SaleRecord;
import com.businessproject.demo.service.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

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

    @GetMapping("/manage/customers")
    public String getProductManageView(@RequestParam("requesterId") String requesterId, Model model) {
        // TODO make request param optional
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("customers", salesRepService.getCustomers(requesterId));

            return "representative/manage_customers";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "representative/invalid_request";
    }

    @GetMapping("/update/customer")
    public String getEditRepresentativeView(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        // TODO make request param optional

        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                model.addAttribute("customer", salesRepService.getCustomerById(targetId));
            } catch (NonExistingCustomerException exception) {
                model.addAttribute("errorMessage", "Requested a non existing customer.");
                return "representative/invalid_request";
            }

            return "representative/update_customer";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized sources are forbidden!");
        return "representative/invalid_request";
    }

    @GetMapping("/delete/customer")
    public String deleteCustomer(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAttribute("requesterId", requesterId);
            try {
                String targetFullName = salesRepService.getCustomerById(targetId).getFullName();
                salesRepService.deleteRepresentativeById(targetId);

                model.addAttribute("fullName", targetFullName);
                model.addAttribute("isError", false);
            } catch (NonExistingCustomerException exception) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMessage", exception.getMessage());
            }
            return "representative/customer_deleted";
        }
        model.addAttribute("errorMessage", "Request from an unauthorized source are forbidden!");
        return "representative/invalid_request";
    }

    @GetMapping("/promotion/products")
    public String getPromotionCreateView(@RequestParam("requesterId") String requesterId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("requesterId", requesterId);
                put("promoInfo", salesRepService.getPromoInfo(requesterId));
                put("nonPromoProducts", salesRepService.getNonPromoProducts(requesterId));
            }});
            return "representative/promotions";
        }
        return "representative/invalid_request";
    }

    @GetMapping("/new/promotion")
    public String getPromoProductView(@RequestParam("requesterId") String requesterId, @RequestParam("targetId") String targetId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            try {
                Product product = salesRepService.getProductById(targetId);
                model.addAllAttributes(new HashMap<String, Object>() {{
                    put("requesterId", requesterId);
                    put("productName", product.getProductName());
                    put("productPrice", product.getPrice());
                    put("targetId", targetId);
                }});
                return "representative/create_promotion";
            } catch (NonExistingProductException exception) {
                model.addAttribute("errorMessage", "Requested a non existing product");
                return "representative/invalid_request";
            }
        }
        return "representative/invalid_request";
    }

    @GetMapping("/new/sale")
    public String getSellProductView(@RequestParam("requesterId") String requesterId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("requesterId", requesterId);
                put("customers", salesRepService.getCustomers(requesterId));
                put("products", salesRepService.getProducts());
            }});
            return "representative/create_sale";
        }
        return "representative/invalid_request";
    }

    @GetMapping("/sales")
    public String getSalesView(@RequestParam("requesterId") String requesterId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("requesterId", requesterId);
                put("sales", salesRepService.getSales(requesterId));
            }});
            return "representative/sales_view";
        }
        return "representative/invalid_request";
    }

    @GetMapping("/products")
    public String getProductsView(@RequestParam("requesterId") String requesterId, Model model) {
        if (salesRepService.existsSalesRepById(requesterId)) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("requesterId", requesterId);
                put("products", salesRepService.getProducts());
            }});
            return "representative/products_view";
        }
        return "representative/invalid_request";
    }

    @PostMapping("/new/customer")
    public String addRepresentative(@Valid @ModelAttribute("product") Customer customer, Model model) {
        try {
            salesRepService.saveCustomer(customer);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("fullName", customer.getFullName());
                put("requesterId", customer.getManagedById());
            }});
        } catch (PhoneNumberAlreadyExists exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", customer.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "representative/invalid_request";
        }
        return "representative/customer_added";
    }

    @PostMapping("/update/customer")
    public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer, Model model) {
        try {
            salesRepService.updateCustomer(customer);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", customer.getManagedById());
            }});
        } catch (PhoneNumberAlreadyExists | NonExistingCustomerException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", customer.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "representative/invalid_request";
        }
        return "representative/customer_updated";
    }

    @PostMapping("/new/promotion")
    public String addPromoEvent(@Valid @ModelAttribute("promoEvent") PromoEvent promoEvent, Model model) {
        try {
            salesRepService.savePromoEvent(promoEvent);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", promoEvent.getManagedById());
            }});
        } catch (NonExistingProductException | PromotionAlreadyActive | MismatchedDateException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", promoEvent.getManagedById());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "representative/invalid_request";
        }
        return "representative/promotion_created";
    }

    @PostMapping("/new/sale")
    public String addSaleRecord(@Valid @ModelAttribute("saleRecord") SaleRecord saleRecord, Model model) {
        try {
            salesRepService.saveSaleRecord(saleRecord);
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", false);
                put("requesterId", saleRecord.getSalesRepId());
            }});
        } catch (NonExistingProductException | NonExistingCustomerException | InsufficientQuantityException exception) {
            model.addAllAttributes(new HashMap<String, Object>() {{
                put("isError", true);
                put("errorMessage", exception.getMessage());
                put("requesterId", saleRecord.getSalesRepId());
            }});
        } catch (AuthorizationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "representative/invalid_request";
        }
        return "representative/sale_created";
    }

}
