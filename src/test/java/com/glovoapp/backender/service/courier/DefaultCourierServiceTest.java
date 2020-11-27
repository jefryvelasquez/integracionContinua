package com.glovoapp.backender.service.courier;

import com.glovoapp.backender.API;
import com.glovoapp.backender.exception.CourierNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {API.class})
@ExtendWith(SpringExtension.class)
public class DefaultCourierServiceTest {

    @Autowired
    CourierService courierService;

    @Test
    void dummy() {
        assertNotNull(courierService.findBy("courier-1"));
    }

    @Test
    void dummy2() {
        assertThrows(CourierNotFoundException.class, () -> courierService.findBy("courier-0"));
    }

}
