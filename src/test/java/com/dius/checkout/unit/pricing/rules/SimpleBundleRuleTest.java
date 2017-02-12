package com.dius.checkout.unit.pricing.rules;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.OrderImpl;
import com.dius.checkout.pricing.rules.Rule;
import com.dius.checkout.pricing.rules.SimpleBundleRule;

public class SimpleBundleRuleTest {

    private static final String ITEM_A = "sku1";
    private static final String ITEM_B = "sku2";

    private OrderImpl order;

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
    }

    @Test
    public void shouldApplyExactlyOneFreeItem() {

        Rule rule = new SimpleBundleRule(ITEM_A, ITEM_B);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(1L);
        when(order.getTotalQuantity(ITEM_B)).thenReturn(1L);

        rule.apply(order);

        verify(order).incrementFreeQuantity(ITEM_B, 1L);
    }

    @Test
    public void shouldApplyMoreThanOneFreeItems() {

        Rule rule = new SimpleBundleRule(ITEM_A, ITEM_B);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(10L);
        when(order.getTotalQuantity(ITEM_B)).thenReturn(2L);

        rule.apply(order);

        verify(order).incrementFreeQuantity(ITEM_B, 2L);
    }

    @Test
    public void shouldApplyZeroFreeItems() {

        Rule rule = new SimpleBundleRule(ITEM_A, ITEM_B);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(0L);
        when(order.getTotalQuantity(ITEM_B)).thenReturn(0L);

        rule.apply(order);

        verify(order, times(0)).incrementFreeQuantity(anyString(), anyLong());
    }
}
