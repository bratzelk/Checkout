package com.dius.checkout;

import java.math.BigDecimal;

public class Item {

    private String sku;
    private String name;
    private BigDecimal price;
    
    public Item(String sku, String name, BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }
    
    public Item(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
