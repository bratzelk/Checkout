package com.dius.checkout;

import java.math.BigDecimal;

public interface Order {

    Long getTotalQuantity(String sku);

    Long getFreeQuantity(String sku);

    void setFreeQuantity(String sku, Long free);

    void incrementFreeQuantity(String sku, Long free);

    BigDecimal getFinalUnitPrice(String sku);

    void setDiscountedUnitPrice(String sku, BigDecimal discountedPrice);

    BigDecimal calculateTotal();

}
