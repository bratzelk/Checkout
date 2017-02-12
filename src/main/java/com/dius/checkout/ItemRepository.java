package com.dius.checkout;

import java.math.BigDecimal;

public interface ItemRepository {

    void add(Item item);
    
    Boolean exists(String sku);
    String getName(String sku);
    BigDecimal getPrice(String sku);
}
