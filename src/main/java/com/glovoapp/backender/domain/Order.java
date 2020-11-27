package com.glovoapp.backender.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class Order {
    private String id;
    private String description;
    private Boolean food;
    private Boolean vip;
    private Location pickup;
    private Location delivery;

    public Order withId(String id) {
        this.id = id;
        return this;
    }

    public Order withDescription(String description) {
        this.description = description;
        return this;
    }

    public Order withFood(Boolean food) {
        this.food = food;
        return this;
    }

    public Order withVip(Boolean vip) {
        this.vip = vip;
        return this;
    }

    public Order withPickup(Location pickup) {
        this.pickup = pickup;
        return this;
    }

    public Order withDelivery(Location delivery) {
        this.delivery = delivery;
        return this;
    }
}
