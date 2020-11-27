package com.glovoapp.backender.repository.courier;

import com.glovoapp.backender.domain.Courier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class DefaultCourierRepository implements CourierRepository {
    private static final String COURIERS_FILE = "/couriers.json";
    private static final List<Courier> couriers;

    static {
        try (Reader reader = new InputStreamReader(DefaultCourierRepository.class.getResourceAsStream(COURIERS_FILE))) {
            Type type = new TypeToken<List<Courier>>() {
            }.getType();
            couriers = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Courier> findById(String courierId) {
        Assert.notNull(courierId, "courierId argument is required; it must not be null"); // TODO Add test case scenario covering it.
        return couriers.stream()
                .filter(courier -> courierId.equals(courier.getId()))
                .findFirst();
    }

    @Override
    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }
}
