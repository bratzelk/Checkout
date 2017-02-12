package com.dius.checkout.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.dius.checkout.Item;
import com.dius.checkout.ItemRepository;
import com.dius.checkout.ItemRepositoryImpl;

public class ItemRepositoryTest {

    private final static String SKU_A = "xyz";
    private final static String NAME_A = "Item A";
    private final static BigDecimal PRICE_A = BigDecimal.ONE;

    private ItemRepository itemRepository;
    private Item item;

    @Before
    public void setup() {
        itemRepository = new ItemRepositoryImpl();
        item = new Item(SKU_A, NAME_A, PRICE_A);
    }

    @Test
    public void shouldReturnFalseIfItemDoesNotExist() {
        Boolean actual = itemRepository.exists(SKU_A);

        assertFalse(actual);
    }

    @Test
    public void shouldAddItem() {
        itemRepository.add(item);

        Boolean actual = itemRepository.exists(SKU_A);

        assertTrue(actual);
    }

    @Test
    public void shouldGetName() {
        itemRepository.add(item);
        String name = itemRepository.getName(SKU_A);

        boolean nameEqual = NAME_A.equals(name);

        assertTrue(nameEqual);
    }

    @Test
    public void shouldGetPrice() {
        itemRepository.add(item);
        BigDecimal price = itemRepository.getPrice(SKU_A);

        boolean priceEqual = PRICE_A.equals(price);

        assertTrue(priceEqual);
    }

}
