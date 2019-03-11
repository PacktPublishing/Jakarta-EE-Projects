package org.jakartaeeprojects.coffee.orders.control;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.CoffeeRequest;

import java.util.Collection;
import java.util.Optional;

@OrderJPA
public class OrderJPARepository implements OrderRepository {

    @Override
    public CoffeeOrder create(CoffeeRequest request) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<CoffeeOrder> getById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Collection<CoffeeOrder> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
