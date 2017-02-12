package com.dius.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository {

    Map<String, Item> itemDb;

    public ItemRepositoryImpl() {
        itemDb = new HashMap<String, Item>();
    }

    @Override
    public void add(Item item) {
        itemDb.put(item.getSku(), item);
    }

    @Override
    public Boolean exists(String sku) {
        return itemDb.get(sku) != null;
    }

    @Override
    public String getName(String sku) {
        if (itemDb.get(sku) == null) {
            throw new IllegalArgumentException();
        }
        return itemDb.get(sku).getName();
    }

    @Override
    public BigDecimal getPrice(String sku) {
        if (itemDb.get(sku) == null) {
            throw new IllegalArgumentException();
        }
        return itemDb.get(sku).getPrice();
    }

}
