package com.glovoapp.backender.service.order;

import com.glovoapp.backender.core.DistanceSlot;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.repository.order.OrderRepository;
import com.glovoapp.backender.service.order.filter.OrderFilter;
import com.glovoapp.backender.service.order.sort.OrderSortChain;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderFilter orderFilter;
    private final List<DistanceSlot> distanceSlots;
    private final OrderSortChain orderSortChain;

    DefaultOrderService(OrderFilter orderFilter,
                        OrderSortChain orderSortChain,
                        List<DistanceSlot> distanceSlots,
                        OrderRepository orderRepository) {
        this.orderFilter = orderFilter;
        this.orderSortChain = orderSortChain;
        this.distanceSlots = distanceSlots;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderVM> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderVM> findBy(Courier courier) {
        Assert.notNull(courier, "courier argument is required; it must not be null");

        List<Order> filteredOrders = this.filterBy(courier);
        Map<DistanceSlot, List<Order>> ordersByDistanceSlots = this.groupBySlotDistance(filteredOrders, courier);
        return this.sortByPriority(ordersByDistanceSlots, courier);
    }

    private List<Order> filterBy(Courier courier) {
        return orderRepository.findAll()
                .stream()
                .filter(orderFilter.supportsSpecialOrder(courier))
                .filter(orderFilter.closeTo(courier))
                .collect(Collectors.toList());
    }

    private Map<DistanceSlot, List<Order>> groupBySlotDistance(List<Order> filteredOrders, Courier courier) {
        Map<DistanceSlot, List<Order>> ordersBySlotDistance = new LinkedHashMap<>();
        for (DistanceSlot distanceSlot : distanceSlots) {
            List<Order> orders = filteredOrders
                    .stream()
                    .filter(orderFilter.prioritise(courier, distanceSlot))
                    .collect(Collectors.toList());
            ordersBySlotDistance.put(distanceSlot, orders);
        }
        return ordersBySlotDistance;
    }

    private List<OrderVM> sortByPriority(Map<DistanceSlot, List<Order>> ordersByDistanceSlots, Courier courier) {
        return ordersByDistanceSlots
                .entrySet()
                .stream()
                .flatMap(e -> e.getValue()
                        .stream()
                        .sorted(orderSortChain.nearby(courier))
                        .map(order -> new OrderVM(
                                order.getId(),
                                order.getDescription())))
                .collect(Collectors.toList());
    }
}
