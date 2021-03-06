package com.businessproject.demo.model.dbmodels;

import com.businessproject.demo.configuration.IValidationPatterns;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Document(collection = "Customers")
public class Customer {
    private String id;

    @NotNull(message = "Managed by was not provided!")
    private String managedById;

    @NotNull(message = "First name was not provided!")
    @Pattern(regexp = IValidationPatterns.NAME_PATTERN, message = "Not a valid first name was provided!")
    @Size(min = 2, max = 15, message = "First name should be between 2 and 15 characters.")
    private String firstName;

    @NotNull(message = "Last name was not provided!")
    @Pattern(regexp = IValidationPatterns.NAME_PATTERN, message = "Not a valid last name was provided!")
    @Size(min = 2, max = 15, message = "Last name should be between 2 and 15 characters.")
    private String lastName;

    @NotNull(message = "Phone number was not provided!")
    @Pattern(regexp = IValidationPatterns.PHONE_PATTERN, message = "Not a valid phone number!")
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagedById() {
        return managedById;
    }

    public void setManagedById(String managedById) {
        this.managedById = managedById;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}
