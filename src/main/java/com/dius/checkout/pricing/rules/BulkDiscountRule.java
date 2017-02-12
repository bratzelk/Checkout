package com.dius.checkout.pricing.rules;

import java.math.BigDecimal;

import com.dius.checkout.OrderImpl;

public class BulkDiscountRule implements Rule {

    private final String sku;
    private final int bulkQuantity;
    private final BigDecimal discountedPrice;

    public BulkDiscountRule(String sku, int bulkQuantity, BigDecimal discountedPrice) {
        this.sku = sku;
        this.bulkQuantity = bulkQuantity;
        this.discountedPrice = discountedPrice;
    }

    @Override
    public OrderImpl apply(OrderImpl order) {
        Long purchaseQuantity = order.getTotalQuantity(sku);

        if (purchaseQuantity >= bulkQuantity) {
            order.setDiscountedUnitPrice(sku, discountedPrice);
        }
        return order;
    }

}
