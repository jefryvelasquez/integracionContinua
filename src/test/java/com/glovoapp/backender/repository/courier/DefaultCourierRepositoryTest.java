package com.glovoapp.backender.repository.courier;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Vehicle;
import com.glovoapp.backender.repository.courier.DefaultCourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCourierRepositoryTest {

    DefaultCourierRepository courierRepository;

    @BeforeEach
    public void setup() {
        courierRepository = new DefaultCourierRepository();
    }

    @Test
    @DisplayName("Should return and match courier's data")
    void findOneExisting() {
        Optional<Courier> courier = courierRepository.findById("courier-1");

        Courier expected = Courier.builder()
                .id("courier-1")
                .box(true)
                .name("Manolo Escobar")
                .vehicle(Vehicle.MOTORCYCLE)
                .location(new Location(10d, 12d))
                .build();

        assertTrue(courier.isPresent());
        assertEquals(expected, courier.get());
    }

    @Test
    @DisplayName("Shouldn't return courier data")
    void findOneNotExisting() {
        Optional<Courier> courierNotFound = courierRepository.findById("bad-courier-id");
        assertFalse(courierNotFound.isPresent());
    }

    @Test
    @DisplayName("Should return ALL couriers available")
    void findAll() {
        List<Courier> all = new DefaultCourierRepository().findAll();
        assertFalse(all.isEmpty());
    }
}