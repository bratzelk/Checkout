package com.dius.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class to encapsulate the Order being checked-out
 *
 * This allows us to potentially give the user more information about their
 * order, rather than crudely showing them the final price only.
 *
 */
public class OrderImpl implements Order {

    ItemRepository itemRepository;

    // An order is comprised of rows indexed/grouped by SKU
    Map<String, OrderRow> order;

    public OrderImpl(ItemRepository itemRepository, List<String> skus) {
        this.itemRepository = itemRepository;

        if (null == skus || null == itemRepository) {
            throw new IllegalArgumentException();
        }

        // Group purchases by item code and total their purchase quantities
        Map<String, Long> purchaseCount = skus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Create simple datastructure to hold the order details
        order = purchaseCount.entrySet().stream().collect(HashMap<String, OrderRow>::new, (map, item) -> {
            String sku = item.getKey();
            Long quantity = item.getValue();
            map.put(sku, initRow(sku, quantity));
        }, HashMap<String, OrderRow>::putAll);
    }

    private OrderRow initRow(String sku, Long quantity) {
        OrderRow row = new OrderRow(quantity, 0L, itemRepository.getPrice(sku), itemRepository.getPrice(sku));
        return row;
    }

    @Override
    public Long getTotalQuantity(String sku) {
        return null == order.get(sku) ? 0 : order.get(sku).getTotalQuantity();
    }

    @Override
    public Long getFreeQuantity(String sku) {
        return null == order.get(sku) ? 0 : order.get(sku).getFreeQuantity();
    }

    @Override
    public void setFreeQuantity(String sku, Long free) {
        if (null == order.get(sku)) {
            throw new IllegalArgumentException();
        }
        order.get(sku).setFreeQuantity(free);
    }

    @Override
    public void incrementFreeQuantity(String sku, Long free) {
        if (null == order.get(sku)) {
            throw new IllegalArgumentException();
        }
        Long current = getFreeQuantity(sku);
        order.get(sku).setFreeQuantity(current + free);
    }

    @Override
    public BigDecimal getFinalUnitPrice(String sku) {
        return null == order.get(sku) ? BigDecimal.ZERO : order.get(sku).getFinalUnitPrice();
    }

    @Override
    public void setDiscountedUnitPrice(String sku, BigDecimal discountedPrice) {
        if (null == order.get(sku)) {
            throw new IllegalArgumentException();
        }
        order.get(sku).setFinalUnitPrice(discountedPrice);
    }

    /**
     * Calculate the total price for the order. For each row, simply subtract
     * the free items from the total items and multiply by the final unit price.
     * Then sum each row.
     *
     * @return The total price of the order
     */
    @Override
    public BigDecimal calculateTotal() {
        return order.values().stream()
                .map(e -> e.getFinalUnitPrice()
                        .multiply(BigDecimal.valueOf(e.getTotalQuantity() - e.getFreeQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return this.order.toString();
    }
}
