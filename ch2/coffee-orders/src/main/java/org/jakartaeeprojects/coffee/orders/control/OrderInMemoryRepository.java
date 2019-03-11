package org.jakartaeeprojects.coffee.orders.control;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.CoffeeRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@OrderInMemory
//@RepositoryType(RepositoryType.Type.IN_MEMORY)
public class OrderInMemoryRepository implements OrderRepository {

    private static final Map<@Positive Long, @Valid CoffeeOrder> ORDER_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong orderCounter = new AtomicLong(1);

    @Inject
    private Logger logger;

    public CoffeeOrder create(CoffeeRequest request) {
        CoffeeOrder order = new CoffeeOrder(request, orderCounter.getAndIncrement());
        ORDER_MAP.put(order.getId(), order);

        logger.log(Level.INFO, "Created order " + order.getId());
        return order;
    }

    public Optional<CoffeeOrder> getById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ORDER_MAP.get(id));
    }

    public Collection<CoffeeOrder> getAll() {
        return ORDER_MAP.values();
    }
}
