package com.glovoapp.backender.service.order.sort;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.core.DistanceCalculator;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
class LocationOrderSortImpl implements OrderSort {

    private Courier courier;

    @Override
    public Comparator<Order> sort() {
        return Comparator.comparingDouble(order ->
                DistanceCalculator.calculateDistance(courier.getLocation(), order.getPickup()));
    }

    @Override
    public void near(Courier courier) {
        this.courier = courier;
    }
}
