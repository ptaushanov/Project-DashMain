package com.businessproject.demo.model.info;

import com.businessproject.demo.model.dbmodels.SalesRepresentative;

import javax.validation.constraints.NotNull;

// Sales Representative information model for use in the sale records
public class SalesRepInfo {
    private String fullName;
    private String username;

    public static SalesRepInfo ofSalesRepresentative(@NotNull SalesRepresentative salesRep) {
        return new SalesRepInfo(salesRep.getFullName(), salesRep.getUsername());
    }

    public SalesRepInfo(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
