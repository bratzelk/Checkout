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
import com.dius.checkout.pricing.rules.XForYRule;

public class XForYRuleTest {

    private OrderImpl order;
    private static final String SKU = "xyz";

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatedWithInvalidParams() {
        new XForYRule("", 2, 2);
    }

    @Test
    public void shouldApplyExactlyOneFreeItem() {

        Rule rule = new XForYRule(SKU, 3, 2);
        when(order.getTotalQuantity(SKU)).thenReturn(3L);

        rule.apply(order);

        verify(order).incrementFreeQuantity(SKU, 1L);
    }

    @Test
    public void shouldApplyMoreThanOneFreeItems() {

        Rule rule = new XForYRule(SKU, 3, 2);
        when(order.getTotalQuantity(SKU)).thenReturn(6L);

        rule.apply(order);

        verify(order).incrementFreeQuantity(SKU, 2L);
    }

    @Test
    public void shouldApplyZeroFreeItems() {

        Rule rule = new XForYRule(SKU, 3, 2);
        when(order.getTotalQuantity(SKU)).thenReturn(0L);

        rule.apply(order);

        verify(order, times(0)).incrementFreeQuantity(anyString(), anyLong());
    }
}
