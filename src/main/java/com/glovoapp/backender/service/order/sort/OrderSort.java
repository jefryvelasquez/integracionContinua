package com.glovoapp.backender.service.order.sort;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;

import java.util.Comparator;

public interface OrderSort {

    Comparator<Order> sort();

    default void near(Courier courier) {
    }
}
