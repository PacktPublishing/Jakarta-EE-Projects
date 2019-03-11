package org.jakartaeeprojects.coffee.orders.entity;

public class OrderChangeEvent {

    private CoffeeOrder order;

    public OrderChangeEvent(CoffeeOrder order) {
        this.order = order;
    }

    public CoffeeOrder getOrder() {
        return order;
    }
}
