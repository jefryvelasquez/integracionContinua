package com.glovoapp.backender.service.order.sort;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import org.apache.commons.collections4.comparators.ComparatorChain;

import java.util.List;

public class OrderSortChain {

    ComparatorChain<Order> chain = new ComparatorChain<>();
    private List<OrderSort> sorters;

    public OrderSortChain(List<OrderSort> orderSorters) {
        this.sorters = orderSorters;
        this.sorters.forEach(e -> chain.addComparator(e.sort()));
    }

    public ComparatorChain<Order> nearby(Courier courier) {
        for (OrderSort orderSort : sorters) {
            orderSort.near(courier);
        }
        return chain;
    }
}
