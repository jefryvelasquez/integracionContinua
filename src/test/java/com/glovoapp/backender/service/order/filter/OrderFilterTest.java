package com.glovoapp.backender.service.order.filter;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.domain.Vehicle;
import com.glovoapp.backender.core.DistanceSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = OrderFilter.class)
@ExtendWith(SpringExtension.class)
class OrderFilterTest {

    @Autowired
    private OrderFilter orderFilter;

    @DisplayName("Courier using GLOVO BOX should supports orders containing special words")
    @ParameterizedTest(name = "Courier using GLOVO BOX should supports orders containing {arguments} word")
    @ValueSource(strings = {"pizza", "flamingo", "cake", "cheesecake"})
    void shouldSupportsSpecialOrders(String description) {
        Courier courier = Courier
                .builder()
                .id("courier-1")
                .box(true)
                .build();

        Order order = new Order().withId("order-1")
                .withDescription(description);

        assertTrue(this.orderFilter.supportsSpecialOrder(courier).test(order));
    }

    @DisplayName("Courier not using GLOVO BOX shouldn't supports orders containing special words")
    @ParameterizedTest(name = "Courier not using GLOVO BOX should''t supports orders containing {arguments} word")
    @ValueSource(strings = {"pizza", "flamingo", "cake", "cheesecake"})
    void shouldNotSupportsSpecialOrders(String description2) {
        Courier courier = Courier
                .builder()
                .id("courier-1")
                .box(false)
                .build();

        Order order = new Order().withId("order-1")
                .withDescription(description2);

        assertFalse(this.orderFilter.supportsSpecialOrder(courier).test(order));
    }

    @DisplayName("Should return order when courier is close than 5km from the order")
    @ParameterizedTest(name = "Courier using {arguments} closer than 5km from the order")
    @EnumSource(value = Vehicle.class, names = {"MOTORCYCLE", "ELECTRIC_SCOOTER", "BICYCLE"})
    void shouldReturnOrdersCloseToCourier(Vehicle vehicle) {
        Courier courier = Courier.builder()
                .id("courier-1")
                .name("Manolo Escobar")
                .box(true)
                .vehicle(vehicle)
                .location(new Location(10d, 12d))
                .build();

        Order order = new Order().withId("order-1")
                .withPickup(new Location(10.00449, 12d));

        assertTrue(this.orderFilter.closeTo(courier).test(order));
    }

    @DisplayName("Should return order when courier is far 5km from the order")
    @ParameterizedTest(name = "Should return order when courier is using {arguments} and it's far 5km from the order")
    @EnumSource(value = Vehicle.class, names = {"MOTORCYCLE", "ELECTRIC_SCOOTER"})
    void shouldReturnOrdersFarFromCourier(Vehicle vehicle) {
        Courier courier = Courier.builder()
                .id("courier-1")
                .name("Manolo Escobar")
                .box(true)
                .vehicle(vehicle)
                .location(new Location(10d, 12d))
                .build();

        Order order = new Order().withId("order-1")
                .withPickup(new Location(10.05, 12d));

        assertTrue(this.orderFilter.closeTo(courier).test(order));
    }

    @Test
    @DisplayName("Shouldn't return order when courier is using BICYCLE and it's far 5km from the order")
    void shouldNotReturnOrdersFarFromCourier() {
        Courier courier = Courier.builder()
                .id("courier-1")
                .name("Manolo Escobar")
                .box(true)
                .vehicle(Vehicle.BICYCLE)
                .location(new Location(10d, 12d))
                .build();

        Order order = new Order().withId("order-1")
                .withPickup(new Location(10.05, 12d));

        assertFalse(this.orderFilter.closeTo(courier).test(order));
    }


    @Test
    @DisplayName("Order should belongs to 500m distance slot")
    void orderShouldBelongTo500mDistanceSlot() {
        Courier courier = Courier.builder()
                .id("courier-1")
                .name("Manolo Escobar")
                .box(true)
                .vehicle(Vehicle.MOTORCYCLE)
                .location(new Location(10d, 12d))
                .build();

        Order order = new Order().withId("order-1")
                .withPickup(new Location(10.001, 12d));

        DistanceSlot distanceSlot = new DistanceSlot(0.0, 0.5);
        assertTrue(this.orderFilter.prioritise(courier, distanceSlot).test(order));
    }

    @Test
    @DisplayName("Order should belongs to 500-1000m distance slot")
    void dummy2() {
        Courier courier = Courier.builder().id("courier-1")
                .name("Manolo Escobar")
                .box(true)
                .vehicle(Vehicle.MOTORCYCLE)
                .location(new Location(10d, 12d))
                .build();

        Order order = new Order().withId("order-1")
                .withPickup(new Location(10.0045, 12d));

        DistanceSlot distanceSlot = new DistanceSlot(0.5, 1.0);
        assertTrue(this.orderFilter.prioritise(courier, distanceSlot).test(order));
    }
}

