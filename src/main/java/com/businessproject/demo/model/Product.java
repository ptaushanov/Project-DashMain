package com.businessproject.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "Products")
public class Product {
    private String id;
    private String managedById;

    @NotNull(message = "Product name was not provided!")
    @Size(min = 2, max = 60, message = "Product name should be between 2 and 60 characters.")
    private String productName;

    @NotNull(message = "Category was not provided!")
    @Size(min = 2, max = 100, message = "Category should be between 2 and 100 characters.")
    private String category;

    @NotNull(message = "Description was not provided!")
    @Size(max = 250, message = "Description should not above 250 characters.")
    private String description;

    @NotNull(message = "Price was not provided!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should have a positive value")
    private BigDecimal price;

    @NotNull(message = "Quantity was not provided!")
    @Min(value = 0, message = "Quantity should be above 0.")
    private int quantity;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
