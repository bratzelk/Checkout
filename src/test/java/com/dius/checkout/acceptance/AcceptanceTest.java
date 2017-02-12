package com.dius.checkout.acceptance;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.Checkout;
import com.dius.checkout.CheckoutImpl;
import com.dius.checkout.Item;
import com.dius.checkout.ItemRepository;
import com.dius.checkout.ItemRepositoryImpl;
import com.dius.checkout.pricing.rules.BulkDiscountRule;
import com.dius.checkout.pricing.rules.Rule;
import com.dius.checkout.pricing.rules.SimpleBundleRule;
import com.dius.checkout.pricing.rules.XForYRule;

public class AcceptanceTest {

    private static final String SKU_IPD = "ipd";
    private static final String SKU_MBP = "mbp";
    private static final String SKU_ATV = "atv";
    private static final String SKU_VGA = "vga";

    private List<Rule> rules;
    private ItemRepository itemRepository;

    /**
     * Here we add our items to the repository and add in our rules
     */
    @Before
    public void setup() {
        itemRepository = new ItemRepositoryImpl();
        itemRepository.add(new Item(SKU_IPD, "Super iPad", 549.99));
        itemRepository.add(new Item(SKU_MBP, "MacBook Pro", 1399.99));
        itemRepository.add(new Item(SKU_ATV, "Apple TV", 109.50));
        itemRepository.add(new Item(SKU_VGA, "VGA adapter", 30.00));

        rules = new ArrayList<Rule>();
        rules.add(new XForYRule(SKU_ATV, 3, 2));
        rules.add(new BulkDiscountRule(SKU_IPD, 4, BigDecimal.valueOf(499.99)));
        rules.add(new SimpleBundleRule(SKU_MBP, SKU_VGA));
    }

    @Test
    public void scenario1() {
        Checkout checkout = new CheckoutImpl(itemRepository, rules);
        checkout.scan(SKU_ATV);
        checkout.scan(SKU_ATV);
        checkout.scan(SKU_ATV);
        checkout.scan(SKU_VGA);

        BigDecimal expected = BigDecimal.valueOf(249.00);
        BigDecimal actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    public void scenario2() {
        Checkout checkout = new CheckoutImpl(itemRepository, rules);
        checkout.scan(SKU_ATV);
        checkout.scan(SKU_IPD);
        checkout.scan(SKU_IPD);
        checkout.scan(SKU_ATV);
        checkout.scan(SKU_IPD);
        checkout.scan(SKU_IPD);
        checkout.scan(SKU_IPD);

        BigDecimal expected = BigDecimal.valueOf(2718.95);
        BigDecimal actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    public void scenario3() {
        Checkout checkout = new CheckoutImpl(itemRepository, rules);
        checkout.scan(SKU_MBP);
        checkout.scan(SKU_VGA);
        checkout.scan(SKU_IPD);

        BigDecimal expected = BigDecimal.valueOf(1949.98);
        BigDecimal actual = checkout.total();

        assertEquals(expected, actual);
    }
}
