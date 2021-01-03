package com.businessproject.demo.model;

public class PromoInfo {
    private final PromoEvent promoEvent;
    private final Product promoProduct;

    public PromoInfo(PromoEvent promoEvent, Product promoProduct) {
        this.promoEvent = promoEvent;
        this.promoProduct = promoProduct;
    }

    public PromoEvent getPromoEvent() {
        return promoEvent;
    }

    public Product getPromoProduct() {
        return promoProduct;
    }
}
