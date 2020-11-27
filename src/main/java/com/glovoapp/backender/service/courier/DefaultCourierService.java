package com.glovoapp.backender.service.courier;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.exception.CourierNotFoundException;
import com.glovoapp.backender.repository.courier.CourierRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
class DefaultCourierService implements CourierService {

    private final CourierRepository courierRepository;

    DefaultCourierService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Courier findBy(String id) {
        Assert.notNull(id, "id argument is required; it must not be null");
        return courierRepository
                .findById(id)
                .orElseThrow(() -> new CourierNotFoundException("Courier " + id + " not found"));
    }
}
