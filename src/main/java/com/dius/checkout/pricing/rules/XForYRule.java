package com.dius.checkout.pricing.rules;

import com.dius.checkout.OrderImpl;

/**
 * X items for the price of Y items
 *
 */
public class XForYRule implements Rule {

    private final int x;
    private final int y;
    private final String sku;

    public XForYRule(String sku, int x, int y) {
        this.sku = sku;
        this.x = x;
        this.y = y;

        // Rule must provide a discount to the customer
        if (x <= y) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public OrderImpl apply(OrderImpl order) {
        // Number of free items
        Long itemsPurchased = order.getTotalQuantity(sku);
        Long freeItemsPerRule = (long) (x - y);
        Long timesRuleInvoked = itemsPurchased / x;
        Long freeItems = timesRuleInvoked * freeItemsPerRule;

        if (freeItems > 0) {
            order.incrementFreeQuantity(sku, freeItems);
        }

        return order;
    }

}
