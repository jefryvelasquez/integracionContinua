package com.glovoapp.backender.service.order.sort;

import com.glovoapp.backender.domain.Order;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
class VipOrderSortImpl implements OrderSort {

    @Override
    public Comparator<Order> sort() {
        return Comparator.comparing(Order::getVip).reversed();
    }
}
