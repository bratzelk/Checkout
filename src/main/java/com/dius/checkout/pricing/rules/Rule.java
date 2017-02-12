package com.dius.checkout.pricing.rules;

import com.dius.checkout.OrderImpl;

public interface Rule {

    OrderImpl apply(OrderImpl order);
}

