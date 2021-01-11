package com.businessproject.demo.model.info;

import com.businessproject.demo.model.dbmodels.Product;

import javax.validation.constraints.NotNull;

// Product information model for use in the sale records
public class ProductInfo {
    private String productName;
    private String category;

    public static ProductInfo ofProduct(@NotNull Product product) {
        return new ProductInfo(product.getProductName(), product.getCategory());
    }

    public ProductInfo(String productName, String category) {
        this.productName = productName;
        this.category = category;
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

}
