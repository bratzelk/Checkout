package com.dius.checkout.pricing.rules;

import com.dius.checkout.OrderImpl;

public class SimpleBundleRule implements Rule {

    private final String mainItemSku;
    private final String bundledItemSku;

    public SimpleBundleRule(String mainItemSku, String bundledItemSku) {
        this.mainItemSku = mainItemSku;
        this.bundledItemSku = bundledItemSku;
    }

    @Override
    public OrderImpl apply(OrderImpl order) {
        Long mainItemQuantity = order.getTotalQuantity(mainItemSku);
        Long bundledItemQuantity = order.getTotalQuantity(bundledItemSku);

        Long bundledItemFreeQuantity = Math.min(mainItemQuantity, bundledItemQuantity);

        if (bundledItemFreeQuantity > 0) {
            order.incrementFreeQuantity(bundledItemSku, bundledItemFreeQuantity);
        }

        return order;
    }

}
