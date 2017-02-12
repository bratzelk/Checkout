package com.dius.checkout;

import java.math.BigDecimal;

public class OrderRow {

    private long totalQuantity;
    private long freeQuantity;
    private BigDecimal originalUnitPrice;
    private BigDecimal finalUnitPrice;

    public OrderRow(long totalQuantity, long freeQuantity, BigDecimal originalUnitPrice, BigDecimal finalUnitPrice) {
        this.totalQuantity = totalQuantity;
        this.freeQuantity = freeQuantity;
        this.originalUnitPrice = originalUnitPrice;
        this.finalUnitPrice = finalUnitPrice;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public long getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(long freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public BigDecimal getOriginalUnitPrice() {
        return originalUnitPrice;
    }

    public void setOriginalUnitPrice(BigDecimal originalUnitPrice) {
        this.originalUnitPrice = originalUnitPrice;
    }

    public BigDecimal getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }

    @Override
    public String toString() {
        return "[totalQuantity=" + totalQuantity + ", freeQuantity=" + freeQuantity + ", originalUnitPrice="
                + originalUnitPrice + ", finalUnitPrice=" + finalUnitPrice + "]";
    }

}
