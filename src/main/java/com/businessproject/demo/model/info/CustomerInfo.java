package com.businessproject.demo.model.info;

import com.businessproject.demo.model.dbmodels.Customer;

import javax.validation.constraints.NotNull;

// Customer information model for use in the sale records
public class CustomerInfo {
    private String fullName;
    private String phoneNumber;

    public static CustomerInfo ofCustomer(@NotNull Customer customer) {
        return new CustomerInfo(customer.getFullName(), customer.getPhoneNumber());
    }

    public CustomerInfo(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
