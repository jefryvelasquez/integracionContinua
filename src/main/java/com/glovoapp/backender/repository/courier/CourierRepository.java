package com.glovoapp.backender.repository.courier;

import com.glovoapp.backender.domain.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    Optional<Courier> findById(String courierId);
    List<Courier> findAll();
}
