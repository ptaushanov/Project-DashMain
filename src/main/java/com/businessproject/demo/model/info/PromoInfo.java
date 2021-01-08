package com.businessproject.demo.model.info;

import com.businessproject.demo.model.dbmodels.Product;
import com.businessproject.demo.model.dbmodels.PromoEvent;

import javax.validation.constraints.NotNull;

public class PromoInfo {
    private final PromoEvent promoEvent;
    private final Product promoProduct;
    private final String status;

    public PromoInfo(@NotNull PromoEvent promoEvent, @NotNull Product promoProduct, String status) {
        this.promoEvent = promoEvent;
        this.promoProduct = promoProduct;
        this.status = status;
    }

    public PromoEvent getPromoEvent() {
        return promoEvent;
    }

    public Product getPromoProduct() {
        return promoProduct;
    }

    public String getStatus() {
        return status;
    }
}
