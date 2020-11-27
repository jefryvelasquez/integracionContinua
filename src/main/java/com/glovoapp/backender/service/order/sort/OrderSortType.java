package com.glovoapp.backender.service.order.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSortType {

    VIP(new VipOrderSortImpl()),
    FOOD(new FoodOrderSortImpl()),
    LOCATION(new LocationOrderSortImpl());

    private OrderSort instance;

    OrderSortType() {
    }
}
