package com.glovoapp.backender.core.configuration;

import com.glovoapp.backender.core.DistanceSlot;
import com.glovoapp.backender.service.order.sort.OrderSortChain;
import com.glovoapp.backender.service.order.sort.OrderSortType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(value = OrderAutoConfigurationProperties.class)
public class OrderAutoConfiguration {

    @Bean
    public List<DistanceSlot> distanceSlots(
            @Value("${backender.order.prioritise.max-distance}") Double maxDistance,
            @Value("${backender.order.prioritise.slot-distance}") Double slotDistance) {
        Double currentValue = 0d;
        List<DistanceSlot> distanceSlots = new ArrayList<>();

        for (double distance = slotDistance; distance <= maxDistance; distance += slotDistance) {
            distanceSlots.add(new DistanceSlot(currentValue, distance));
            currentValue = distance;
        }
        distanceSlots.add(new DistanceSlot(currentValue, Double.MAX_VALUE));
        return distanceSlots;
    }

    // TODO Review it
    @Bean
    public OrderSortChain orderSorterGroup(
            @Value("#{'${backender.order.sorters}'.toUpperCase().split(',')}") List<OrderSortType> orderSortTypes) {
        return new OrderSortChain(
                orderSortTypes
                .stream()
                .map(OrderSortType::getInstance)
                .collect(Collectors.toList()));
    }
}
