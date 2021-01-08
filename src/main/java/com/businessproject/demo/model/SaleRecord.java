package com.businessproject.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Document(collection = "SalesCatalog")
public class SaleRecord {
    private String id;

    @NotNull(message = "Customer id was not provided!")
    private String customerId;

    @NotNull(message = "Sales representative id was not provided!")
    private String saleRepId;

    private BigDecimal price;

    @NotNull(message = "Quantity was not provided!")
    @Min(value = 0, message = "Quantity should be above 0.")
    private int quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime madeAt;

    @NotNull(message = "ProductId was not provided!")
    private String productId;

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSaleRepId() {
        return saleRepId;
    }

    public void setSaleRepId(String saleRepId) {
        this.saleRepId = saleRepId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public ZonedDateTime getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(ZonedDateTime madeAt) {
        this.madeAt = madeAt;
    }

}
