package com.dius.checkout.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.ItemRepository;
import com.dius.checkout.OrderImpl;

public class OrderTest {

    private static final String SKU_A = "xyz";
    private static final String SKU_B = "zyx";

    private ItemRepository itemRepository;
    private OrderImpl order;

    @Before
    public void setup() {
        itemRepository = mock(ItemRepository.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNullParamPassedIn() {
        order = new OrderImpl(itemRepository, null);
    }

    @Test
    public void shouldSetQuantities() {
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A, SKU_A, SKU_A));
        Long expected = 3L;
        Long actual = order.getTotalQuantity(SKU_A);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnZeroQuantity() {
        order = new OrderImpl(itemRepository, new ArrayList<String>());
        Long expected = 0L;
        Long actual = order.getTotalQuantity(SKU_A);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSetFreeQuantity() {
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A));
        Long expected = 1L;
        order.setFreeQuantity(SKU_A, expected);
        Long actual = order.getFreeQuantity(SKU_A);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldIncrementFreeQuantity() {
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A));
        Long initial = 3L;
        Long additional = 6L;
        Long expected = 9L;
        order.setFreeQuantity(SKU_A, initial);
        order.incrementFreeQuantity(SKU_A, additional);
        Long actual = order.getFreeQuantity(SKU_A);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSetDiscountPrice() {
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A));
        BigDecimal expected = BigDecimal.valueOf(99.99);
        order.setDiscountedUnitPrice(SKU_A, expected);
        BigDecimal actual = order.getFinalUnitPrice(SKU_A);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldCalculateZeroTotalWhenNothingPurchased() {
        order = new OrderImpl(itemRepository, Arrays.asList());
        BigDecimal expected = BigDecimal.ZERO;

        BigDecimal actual = order.calculateTotal();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldCorrectCalculateTotalForOneItem() {
        BigDecimal itemPrice = BigDecimal.valueOf(22);
        when(itemRepository.getPrice(anyString())).thenReturn(itemPrice);
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A));
        BigDecimal expected = itemPrice;

        BigDecimal actual = order.calculateTotal();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldCorrectCalculateTotalForMoreThanOneItem() {
        BigDecimal itemPrice = BigDecimal.valueOf(22);
        when(itemRepository.getPrice(anyString())).thenReturn(itemPrice);
        order = new OrderImpl(itemRepository, Arrays.asList(SKU_A, SKU_A, SKU_B));
        BigDecimal expected = itemPrice.multiply(BigDecimal.valueOf(3));

        BigDecimal actual = order.calculateTotal();

        assertEquals(expected, actual);
    }

}
