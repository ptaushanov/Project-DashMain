package com.businessproject.demo.model;

public class PromoInfo {
    private final PromoEvent promoEvent;
    private final Product promoProduct;
    private final String status;

    public PromoInfo(PromoEvent promoEvent, Product promoProduct, String status) {
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
