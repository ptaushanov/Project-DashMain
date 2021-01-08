package com.businessproject.demo.model.dbmodels;

import com.businessproject.demo.model.info.CustomerInfo;
import com.businessproject.demo.model.info.ProductInfo;
import com.businessproject.demo.model.info.SalesRepInfo;
import org.springframework.data.annotation.Transient;
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
    @Transient
    private String customerId;

    @NotNull(message = "Sales representative id was not provided!")
    private String salesRepId;

    @NotNull(message = "ProductId was not provided!")
    @Transient
    private String productId;

    private BigDecimal price;

    @NotNull(message = "Quantity was not provided!")
    @Min(value = 0, message = "Quantity should be above 0.")
    private int quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime madeAt;

    private ProductInfo productInfo;
    private CustomerInfo customerInfo;
    private SalesRepInfo salesRepInfo;

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(String salesRepId) {
        this.salesRepId = salesRepId;
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

    public void setPrice(@NotNull BigDecimal price) {
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

    public void setMadeAt(@NotNull ZonedDateTime madeAt) {
        this.madeAt = madeAt;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(@NotNull ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(@NotNull CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public SalesRepInfo getSalesRepInfo() {
        return salesRepInfo;
    }

    public void setSalesRepInfo(@NotNull SalesRepInfo salesRepInfo) {
        this.salesRepInfo = salesRepInfo;
    }

    public BigDecimal getTotalPrice() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}
