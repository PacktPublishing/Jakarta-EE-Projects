package org.jakartaeeprojects.coffee.orders.control;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.CoffeeRequest;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository {

    CoffeeOrder create(CoffeeRequest request);

    Optional<CoffeeOrder> getById(Long id);

    Collection<CoffeeOrder> getAll();
}
