package com.glovoapp.backender.repository.order;

import com.glovoapp.backender.domain.Order;

import java.util.List;

public interface OrderRepository {

    /***
     * Return all orders
     *
     * @return Orders' list
     */
    List<Order> findAll();
}
