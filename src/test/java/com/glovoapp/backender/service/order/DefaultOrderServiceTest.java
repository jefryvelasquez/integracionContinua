package com.glovoapp.backender.service.order;

import com.glovoapp.backender.API;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.glovoapp.backender.domain.Vehicle.MOTORCYCLE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {API.class})
@ExtendWith(SpringExtension.class)
class DefaultOrderServiceTest {

    @Autowired
    private DefaultOrderService orderService;

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnOrders() {
        assertNotNull(orderService.findAll());
    }

    @Test
    @DisplayName("Should return orders for a certain courier")
    void shouldReturnOrdersByCourier() {
        Courier courier = Courier.builder().id("courier-1")
                .box(true)
                .name("Manolo Escobar")
                .vehicle(MOTORCYCLE)
                .location(new Location(10d, 12d))
                .build();
        assertNotNull(orderService.findBy(courier));
    }

    @Test
    @DisplayName("Should throw an IllegalArgumentException when courier is null")
    void shouldReturnOrdersByCourier2() {
        assertThrows(IllegalArgumentException.class, () -> orderService.findBy(null));
    }
}
