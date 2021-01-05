package com.businessproject.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "PromoEvents")
public class PromoEvent {
    @Id
    private String id;

    @NotNull(message = "Start date was not provided!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotNull(message = "End date was not provided!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @NotNull(message = "Product id was not provided!")
    private String productId;
    @NotNull(message = "Managed by was not provided!")
    private String managedById;
    @NotNull(message = "Promotional price was not provided!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Promotional price should have a positive value.")
    private BigDecimal promoPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getManagedById() {
        return managedById;
    }

    public void setManagedById(String managedById) {
        this.managedById = managedById;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
