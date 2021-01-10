package com.businessproject.demo.model.dbmodels;

import com.businessproject.demo.configuration.IValidationPatterns;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Document(collection = "Admins")
public class Admin implements Entity {
    private String id;
    private String addedById;

    @NotNull(message = "First name was not provided!")
    @Pattern(regexp = IValidationPatterns.NAME_PATTERN, message = "Not a valid first name was provided!")
    @Size(min = 2, max = 15, message = "First name should be between 2 and 15 characters.")
    private String firstName;

    @NotNull(message = "Last name was not provided!")
    @Pattern(regexp = IValidationPatterns.NAME_PATTERN, message = "Not a valid last name was provided!")
    @Size(min = 2, max = 15, message = "Last name should be between 2 and 15 characters.")
    private String lastName;

    @NotNull(message = "Username was not provided!")
    @Pattern(regexp = IValidationPatterns.USERNAME_PATTERN, message = "Not a valid username was provided!")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters.")
    private String username;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddedById() {
        return addedById;
    }

    public void setAddedById(String addedById) {
        this.addedById = addedById;
    }

    @Override
    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
