package com.glovoapp.backender.service.order;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.dto.OrderVM;

import java.util.List;

public interface OrderService {

    /***
     * Return all orders available.
     *
     * @return Orders' list
     */
    List<OrderVM> findAll();

    /**
     * Find order assigned for a given <code>Courier</code>
     * @param courier Courier
     * @return Orders' list
     */
    List<OrderVM> findBy(Courier courier);
}
