package com.dius.checkout.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.Checkout;
import com.dius.checkout.CheckoutImpl;
import com.dius.checkout.ItemRepository;
import com.dius.checkout.OrderImpl;
import com.dius.checkout.pricing.rules.BulkDiscountRule;
import com.dius.checkout.pricing.rules.Rule;

public class CheckoutTest {

    private final static String SKU_A = "xyz";

    private ItemRepository itemRepository;
    private List<Rule> rules;
    private Checkout checkout;

    @Before
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        rules = new ArrayList<Rule>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenUnknownItemScanned() {
        checkout = new CheckoutImpl(itemRepository, rules);
        checkout.scan("unknownSKU");
    }

    @Test
    public void shouldScanItem() {
        when(itemRepository.exists(anyString())).thenReturn(true);
        checkout = new CheckoutImpl(itemRepository, rules);

        checkout.scan(SKU_A);

        verify(itemRepository).exists(SKU_A);
    }

    @Test
    public void shouldTotalItems() {
        when(itemRepository.exists(anyString())).thenReturn(true);
        when(itemRepository.getPrice(anyString())).thenReturn(BigDecimal.TEN);
        checkout = new CheckoutImpl(itemRepository, rules);

        checkout.scan(SKU_A);
        BigDecimal expected = BigDecimal.TEN;
        BigDecimal actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldApplyPricingRules() {

        Rule bulkRule = mock(BulkDiscountRule.class);
        rules.add(bulkRule);
        when(bulkRule.apply(any())).thenReturn(new OrderImpl(itemRepository, new ArrayList<String>()));
        when(itemRepository.exists(anyString())).thenReturn(true);
        when(itemRepository.getPrice(anyString())).thenReturn(BigDecimal.TEN);
        checkout = new CheckoutImpl(itemRepository, rules);

        checkout.scan(SKU_A);
        BigDecimal expected = BigDecimal.ZERO;
        BigDecimal actual = checkout.total();

        assertEquals(expected, actual);
        verify(bulkRule).apply(any());
    }
}
