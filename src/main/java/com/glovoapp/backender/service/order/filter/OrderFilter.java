package com.glovoapp.backender.service.order.filter;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.domain.Vehicle;
import com.glovoapp.backender.core.DistanceCalculator;
import com.glovoapp.backender.core.DistanceSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import static com.glovoapp.backender.domain.Vehicle.ELECTRIC_SCOOTER;
import static com.glovoapp.backender.domain.Vehicle.MOTORCYCLE;

@Component
public class OrderFilter {

    @Value("#{'${backender.order.special-words}'.split(',')}")
    private List<String> specialWords;

    @Value("${backender.order.further-distance}")
    private Double furtherDistance;

    public Predicate<Order> supportsSpecialOrder(Courier courier) {
        return order -> {
            String description = order.getDescription().toLowerCase();
            boolean hasSpecialOrder = specialWords.stream().anyMatch(description::contains);
            return !hasSpecialOrder || hasSpecialOrder && courier.getBox();
        };
    }

    public Predicate<Order> closeTo(Courier courier) {
        return order -> {
            double distance = DistanceCalculator.calculateDistance(courier.getLocation(), order.getPickup());
            Vehicle vehicle = courier.getVehicle();
            return distance <= furtherDistance || (vehicle.equals(MOTORCYCLE) || vehicle.equals(ELECTRIC_SCOOTER));
        };
    }

    public Predicate<Order> prioritise(Courier courier, DistanceSlot distanceSlot) {
        return order -> distanceSlot.between(
                DistanceCalculator.calculateDistance(courier.getLocation(), order.getPickup()));

    }
}
