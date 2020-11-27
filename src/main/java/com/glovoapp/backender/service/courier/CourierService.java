package com.glovoapp.backender.service.courier;

import com.glovoapp.backender.domain.Courier;

public interface CourierService {

    /***
     * Find a Courier by <code>id</code> attribute
     *
     * @param id Courier's identification
     * @return Courier
     */
    Courier findBy(String id);
}
