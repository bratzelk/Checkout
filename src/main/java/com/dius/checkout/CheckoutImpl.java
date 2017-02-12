package com.dius.checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dius.checkout.pricing.rules.Rule;

public class CheckoutImpl implements Checkout {

    private final ItemRepository itemRepository;

    private final List<Rule> rules;
    private final List<String> skus;

    private final static Logger LOG = Logger.getLogger(CheckoutImpl.class.getName());

    public CheckoutImpl(ItemRepository itemRepository, List<Rule> rules) {
        this.rules = rules;
        this.itemRepository = itemRepository;
        skus = new ArrayList<String>();
    }

    @Override
    public void scan(String sku) {
        LOG.log(Level.INFO, "Scanning item: {0}", sku);

        if (itemRepository.exists(sku)) {
            skus.add(sku);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public BigDecimal total() {

        OrderImpl order = new OrderImpl(itemRepository, skus);

        LOG.log(Level.INFO, "Order before discounts: {0}", order);
        // Apply each pricing rule to the order
        for (Rule rule : rules) {
            order = rule.apply(order);
        }
        LOG.log(Level.INFO, "Order after discounts: {0}", order);

        BigDecimal total = order.calculateTotal();

        LOG.log(Level.INFO, "Total order cost is: {0}", total);
        return total;
    }

}
