package com.dius.checkout.unit.pricing.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.OrderImpl;
import com.dius.checkout.pricing.rules.BulkDiscountRule;
import com.dius.checkout.pricing.rules.Rule;

public class BulkDiscountRuleTest {

    private OrderImpl order;
    private static final String SKU = "xyz";

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
    }

    @Test
    public void shouldApplyDiscount() {
        BigDecimal discountPrice = new BigDecimal(99.95);
        Rule rule = new BulkDiscountRule(SKU, 10, discountPrice);
        when(order.getTotalQuantity(SKU)).thenReturn(10L);

        rule.apply(order);

        verify(order).setDiscountedUnitPrice(SKU, discountPrice);
    }

    @Test
    public void shouldNotApplyDiscount() {

        BigDecimal discountPrice = new BigDecimal(99.95);
        Rule rule = new BulkDiscountRule(SKU, 10, discountPrice);
        when(order.getTotalQuantity(SKU)).thenReturn(5L);

        rule.apply(order);

        verify(order, times(0)).setDiscountedUnitPrice(SKU, discountPrice);
    }

}
