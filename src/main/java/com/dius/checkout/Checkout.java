package com.dius.checkout;

import java.math.BigDecimal;

public interface Checkout {

    void scan(String sku);
    BigDecimal total();
}
